package se.caroline.s3project;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

public class S3Service {

    private final S3Client s3Client;
    private final String bucket1;
    private final String bucket2;
    private String aktivBucket;

    public S3Service() {
        Dotenv dotenv = Dotenv.load();

        String accessKey = dotenv.get("AWS_ACCESS_KEY");
        String secretKey = dotenv.get("AWS_SECRET_KEY");
        String region = dotenv.get("AWS_REGION");
        this.bucket1 = dotenv.get("AWS_BUCKET1_NAME");
        this.bucket2 = dotenv.get("AWS_BUCKET2_NAME");
        this.aktivBucket = bucket1; // standard-bucket

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public void bytBucket(String bucketNamn) {
        if (bucketNamn.equals(bucket1) || bucketNamn.equals(bucket2)) {
            this.aktivBucket = bucketNamn;
            System.out.println("Byte till bucket: " + aktivBucket);
        } else {
            System.out.println("Fel: Ogiltigt bucket-namn");
        }
    }

    public void visaAktivBucket() {
        System.out.println("Aktiv bucket: " + aktivBucket);
    }

    public void listaFiler() {
        try {
            ListObjectsV2Response response = s3Client.listObjectsV2(
                    ListObjectsV2Request.builder().bucket(aktivBucket).build()
            );

            if (response.contents().isEmpty()) {
                System.out.println("Bucket är tom.");
            } else {
                System.out.println("Filer i bucket:");
                response.contents().forEach(obj -> System.out.println(" - " + obj.key() + " (" + obj.size() + " bytes)"));
            }
        } catch (S3Exception e) {
            System.err.println("Fel vid listning: " + e.awsErrorDetails().errorMessage());
        }
    }

    public void laddaUppFil(String filSokvag) {
        File file = new File(filSokvag);
        if (!file.exists()) {
            System.out.println("Filen finns inte: " + filSokvag);
            return;
        }

        try {
            s3Client.putObject(
                    PutObjectRequest.builder().bucket(aktivBucket).key(file.getName()).build(),
                    Paths.get(filSokvag)
            );
            System.out.println("Filen laddades upp till: " + aktivBucket);
        } catch (S3Exception e) {
            System.err.println("Fel vid uppladdning: " + e.awsErrorDetails().errorMessage());
        }
    }

    public void laddaNerFil(String nyckel, String malSokvag) {
        try {
            new File(malSokvag).getParentFile().mkdirs();

            s3Client.getObject(
                    GetObjectRequest.builder().bucket(aktivBucket).key(nyckel).build(),
                    Paths.get(malSokvag)
            );
            System.out.println("Filen laddades ner till: " + malSokvag);
        } catch (S3Exception e) {
            System.err.println("Fel vid nedladdning: " + e.awsErrorDetails().errorMessage());
        }
    }

    // Extra getter om du vill visa tillgängliga buckets
    public String getBucket1() {
        return bucket1;
    }

    public String getBucket2() {
        return bucket2;
    }
}
