# 🔮 Spring Boot + Gemini API (Text AI Integration)

This project demonstrates how to integrate the **Gemini 1.5 Flash API** by Google into a **Spring Boot** application. It includes endpoints for:

- 🔤 General text generation
- 📝 Text summarization
- ✍️ Text rewriting
- 👨‍💻 Code explanation
- 🌐 Swagger UI for easy testing

---

## 🚀 Features

✅ Uses **Gemini 1.5 Flash** (fast + free model)  
✅ Lightweight and clean Spring Boot REST API  
✅ Swagger UI (`/swagger-ui.html`) for exploration  
✅ Logs and estimates **token usage per request**

---

## 🔧 Setup Instructions

### 1. Clone the repo

```bash
git clone https://github.com/your-username/springboot-gemini-api.git
cd springboot-gemini-api
```

### 2.Add your Google Gemini API Key
   Create a file: `src/main/resources/application.properties` just like the template version.

Get your API key by enabling the [Generative Language API in Google Cloud Console](https://console.cloud.google.com/apis/library/generativelanguage.googleapis.com).

### 3. Build and run

```bash
./mvnw spring-boot:run
```
or with an IDE. then try out the swagger page Visit: `http://localhost:8080/swagger-ui.html`