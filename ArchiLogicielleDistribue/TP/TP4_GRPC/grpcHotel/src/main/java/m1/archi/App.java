package m1.archi;
/*
import com.google.protobuf.InvalidProtocolBufferException;
import proto.AdresseOuterClass;
import proto.HotelOuterClass;
public class App {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        // Créer une instance d'Adresse
        AdresseOuterClass.Adresse adresse = AdresseOuterClass.Adresse.newBuilder()
                .setIdAdresse(1)
                .setNumero("123")
                .setRue("Rue de la Paix")
                .setVille("Paris")
                .setPays("France")
                .setPosition("48.8566,2.3522")
                .build();
        System.out.println(adresse);

        // Sérialiser l'objet Adresse en bytes
        byte[] adresseBytes = adresse.toByteArray();

        // Désérialiser les bytes en objet Adresse
        AdresseOuterClass.Adresse adresseDeserialisee = AdresseOuterClass.Adresse.parseFrom(adresseBytes);
        System.out.println(adresseDeserialisee);

        // Créer une instance d'hotel
        HotelOuterClass.Hotel hotel = HotelOuterClass.Hotel.newBuilder()
                .setIdHotel(1)
                .setNom("Hotel de la Paix")
                .setAdresse(adresse)
                .setNombreEtoiles(5)
                .build();

        System.out.println(hotel);
        System.out.println(hotel.getChambresList());

    }
}
*/