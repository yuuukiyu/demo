# 🔨 ビルドステージ
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# プロジェクト全体をコピー
COPY . .

# テストをスキップしてビルド
RUN mvn clean package -DskipTests

# 🚀 実行ステージ
FROM eclipse-temurin:17-jdk
WORKDIR /app

# ビルドステージで生成された JAR をコピー
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# アプリケーションがリッスンするポート
EXPOSE 8080

# 実行コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
