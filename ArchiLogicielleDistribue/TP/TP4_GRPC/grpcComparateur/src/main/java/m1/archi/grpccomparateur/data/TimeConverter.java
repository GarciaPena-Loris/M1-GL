package m1.archi.grpccomparateur.data;

public class TimeConverter {
    public static java.sql.Timestamp convertTimestamp(com.google.protobuf.Timestamp timestamp) {
        return new java.sql.Timestamp(timestamp.getSeconds() * 1000);
    }

    public static com.google.protobuf.Timestamp convertTimestamp(java.sql.Timestamp timestamp) {
        return com.google.protobuf.Timestamp.newBuilder().setSeconds(timestamp.getTime() / 1000).build();
    }
}
