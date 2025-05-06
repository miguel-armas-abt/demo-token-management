# 📌 Resumen
`<autor>`: Miguel Rodrigo Armas Abt

---

## 📦 [mock-service-v1](mock-service-v1/README.md)
- Simula la generación de tokens para las aplicaciones `APP` y `WEB`.

## 📦 [token-management-v1](token-management-v1/README.md)
- Centraliza la obtención de tokens de las diferentes aplicaciones.
- La solución aplica una integración con redis para almacenar los tokens en caché durante su tiempo de vida (`ttl`).
- Realiza el consumo API RESTful con RestTemplate.

<img src="./diagrams.svg" width="400" height="280">