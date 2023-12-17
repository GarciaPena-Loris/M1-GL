package m1.archi.grpcagence.services;


import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import m1.archi.grpcagence.exceptions.EntityNotFoundException;
import m1.archi.grpcagence.exceptions.InternalErrorException;
import m1.archi.grpcagence.models.Agence;
import m1.archi.grpcagence.models.hotelModels.Hotel;
import m1.archi.grpcagence.models.hotelModels.Offre;
import m1.archi.grpcagence.repositories.AgenceRepository;
import m1.archi.grpcagence.repositories.UtilisateurRepository;
import m1.archi.services.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@GrpcService
public class AgenceServiceImpl extends AgenceServiceGrpc.AgenceServiceImplBase {
    private final AgenceRepository agenceRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Proxy
    @GrpcClient("grpcHotel")
    private HotelServiceGrpc.HotelServiceBlockingStub hotelService;


    @Autowired
    public AgenceServiceImpl(AgenceRepository hotelRepository, UtilisateurRepository offreRepository) {
        this.agenceRepository = hotelRepository;
        this.utilisateurRepository = offreRepository;
    }

    @Override
    public void getAllAgences(Empty request, StreamObserver<AgenceListResponse> responseObserver) {
        try {
            List<Agence> agences = agenceRepository.findAll();
            AgenceListResponse response = AgenceListResponse.newBuilder()
                    .addAllAgences(agences.stream().map(Agence::toProto).collect(Collectors.toList()))
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération des agences", e);
        }
    }

    @Override
    public void getAllIdAgences(Empty request, StreamObserver<IdAgenceListResponse> responseObserver) {
        try {
            List<Long> idAgences = agenceRepository.findAll().stream().map(Agence::getIdAgence).collect(Collectors.toList());
            IdAgenceListResponse response = IdAgenceListResponse.newBuilder()
                    .addAllIdAgence(idAgences)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération des id des agences", e);
        }
    }

    @Override
    public void getAgenceById(GetAgenceByIdRequest request, StreamObserver<AgenceResponse> responseObserver) {
        long idAgence = request.getIdAgence();
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
        try {
            AgenceResponse response = AgenceResponse.newBuilder()
                    .setAgence(agence.toProto())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération de l'agence", e);
        }
    }

    @Override
    public void createAgence(AgenceRequest request, StreamObserver<AgenceResponse> responseObserver) {
        try {
            Agence agence = new Agence(request.getAgence());
            Agence savedAgence = agenceRepository.save(agence);
            AgenceResponse response = AgenceResponse.newBuilder()
                    .setAgence(savedAgence.toProto())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la création de l'agence", e);
        }
    }

    @Override
    public void updateAgence(UpdateAgenceRequest request, StreamObserver<AgenceResponse> responseObserver) {
        try {
            long agenceId = request.getIdAgence();
            Agence newAgence = new Agence(request.getAgence());
            Agence updatedAgence = agenceRepository.findById(agenceId).map(agence -> {
                agence.setNom(newAgence.getNom());
                agence.setReductionHotels(newAgence.getReductionHotels());
                agence.setListeUtilisateurs(newAgence.getListeUtilisateurs());
                return agenceRepository.save(agence);
            }).orElseGet(() -> {
                newAgence.setIdAgence(agenceId);
                return agenceRepository.save(newAgence);
            });

            AgenceResponse response = AgenceResponse.newBuilder().setAgence(updatedAgence.toProto()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la mise à jour de l'agence", e);
        }
    }

    @Override
    public void deleteAgence(DeleteAgenceRequest request, StreamObserver<Empty> responseObserver) {
        long idAgence = request.getIdAgence();
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
        try {
            agenceRepository.delete(agence);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la suppression de l'agence", e);
        }
    }


    // Fonction de Recherche
    @Override
    public void rechercherChambresById(RechercherChambresByIdRequest request, StreamObserver<RechercherChambresResponse> responseObserver) {
    long idAgence = request.getIdAgence();
    Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));

    try {
        List<List<Offre>> listeOffresParHotels = new ArrayList<>();
        // private Map<Integer, Hotel> reductionHotels;
        // Pour chaque element de a map
        for (Map.Entry<Integer, Hotel> reductionHotel : agence.getReductionHotels().entrySet()) {
            // Appel de la fonction de recherche de l'hotel via le proxy
            List<Offre> offresHotel = hotelService.rechercherChambres(
                    reductionHotel.getIdHotel(),
                    request.getVille(),
                    request.getDateArrivee(),
                    request.getDateDepart(),
                    request.getPrixMin(),
                    request.getPrixMax(),
                    request.getNombreEtoiles(),
                    request.getNombrePersonne()
            );

            // Apply the reduction
            if (offresHotel != null && !offresHotel.isEmpty()) {
                for (Offre offre : offresHotel) {
                    offre.setPrixAvecReduction(Math.round(offre.getPrix() * (1 - reductionHotel.getReduction() / 100.0) * 100.0) / 100.0);

                    // Save the offer
                    try {
                        hotelService.updateOffreHotel(offre.getIdOffre(), offre);
                    } catch (Exception ex) {
                        throw new InternalErrorException("Hotel problem: " + ex.getMessage(), ex);
                    }
                }
                listeOffresParHotels.add(offresHotel);
            }
        }

        RechercherChambresResponse response = RechercherChambresResponse.newBuilder()
                .addAllOffre(listeOffresParHotels.stream().flatMap(List::stream).map(Offre::toProto).collect(Collectors.toList()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    } catch (Exception ex) {
        throw new InternalErrorException("Hotel problem: " + ex.getMessage(), ex);
    }
}
}
