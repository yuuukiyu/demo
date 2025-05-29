# ベースイメージ
FROM eclipse-temurin:17-jdk

# 作業ディレクトリ設定
WORKDIR /app

# jar をコピー
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# ポート開放
EXPOSE 8080

# 起動コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]