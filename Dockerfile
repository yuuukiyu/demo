# 🔨 ビルドステージ：Maven で JAR を作成
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# プロジェクト全体をコピー
COPY . .

# テストをスキップして JAR ビルド
RUN mvn clean package -DskipTests

# 🚀 実行ステージ：ビルド済み JAR を実行
FROM eclipse-temurin:17-jdk
WORKDIR /app

# ビルド済み JAR をコピー
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
# 使用するポートを指定
EXPOSE 8080

# Spring Boot アプリケーションの起動
ENTRYPOINT ["java", "-jar", "app.jar"]
