# Crypto Recommendation Project

This web application provides beginner-friendly cryptocurrency investment recommendations based on user-defined investment preferences and financial information. 
It uses:
- **CoinGecko API** to retrieve live market data
- **API Ninjas Income Tax Calculator API** to estimate U.S. income taxes

## Configuration

Before running the application, you must create a file named `application.properties` inside: src/main/resources/

You will also need free API keys from the following providers:

- CoinGecko: [https://www.coingecko.com/en/api](https://www.coingecko.com/en/api)
- API Ninjas: [https://api-ninjas.com/api/incometaxcalculator](https://api-ninjas.com/api/incometaxcalculator)

Make sure you have the following installed:

- [Java 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Git](https://git-scm.com/)

<details>
<summary>Click to view required <code>application.properties</code> content</summary>

# Database configuration
spring.application.name=crypto-maximization-app
spring.datasource.url=jdbc:postgresql://localhost:5432/cryptoapp
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA and Hibernate settings
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
#spring.jpa.show-sql=true

# CoinGecko API
coingecko.api.url=https://api.coingecko.com/api/v3
coingecko.api.key=

# API Ninjas Income Tax Calculator
apininjas.api.url=https://api-ninjas.com/api/incometaxcalculator
income.calculator.key=

# Enable caching
spring.cache.type=caffeine

# Default crypto symbols (modify as needed)
crypto.default.symbols=usds,ethereum,solana,tether,dogecoin,ai-companions,litecoin,polygon,cosmos,chainlink,vechain,cardano,popcat,binancecoin,dai,usd-coin
</details>
