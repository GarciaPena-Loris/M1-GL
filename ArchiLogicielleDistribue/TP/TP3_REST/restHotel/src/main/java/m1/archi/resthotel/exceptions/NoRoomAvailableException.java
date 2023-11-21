package m1.archi.resthotel.exceptions;

public class NoRoomAvailableException extends HotelException {
    public NoRoomAvailableException() {
    }

    public NoRoomAvailableException(String message) {
        super(message);
    }
}
