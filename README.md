# 📦 S3 Java Console Application

Detta är ett skolprojekt skapat som en del av Cloudutbildningen  
Applikationen är en **kommandoradsbaserad Java-applikation** som interagerar med **Amazon S3** via AWS SDK.

## 🎯 Funktionalitet (G-nivå)

- ✅ Lista filer i en S3-bucket
- ✅ Ladda upp fil till en bucket
- ✅ Ladda ner fil från en bucket
- ✅ Automatisk export av JAR-fil till separat artifacts-bucket via CodeBuild

## ⚙️ Krav

- Java 17 (t.ex. Corretto 17)
- Maven
- AWS-konto med S3-tillgång
- `.env`-fil med dina AWS-nycklar och buckets

## 🔐 Miljövariabler – `.env`

Applikationen kräver en `.env`-fil i projektroten.  
Se `.env.example` för mall.

```env
AWS_ACCESS_KEY=din_access_key
AWS_SECRET_KEY=din_secret_key
AWS_REGION=eu-north-1
AWS_BUCKET1_NAME=din_bucket_1
AWS_BUCKET2_NAME=din_bucket_2
