## POC Spring Boot - Redis
`<autor>`: Miguel Rodrigo Armas Abt

## 📋 Pre requisitos
> ⚙️ **Instalar herramientas**<br>
> `Java 17+`, `Maven 3.9.1+`, `Redis`, `Docker`, `Minikube`, `Kubectl`
>

## ▶️ Docker

⚙️ Crear imágenes
```shell
eval $(minikube docker-env --shell bash)
docker build -t miguelarmasabt/mock-service:v1.0.1 -f ./mock-service-v1/Dockerfile ./mock-service-v1
docker build -t miguelarmasabt/token-management:v1.0.1 -f ./token-management-v1/Dockerfile ./token-management-v1
```

⚙️ Ver imágenes
```shell
docker image ls
```

⚙️ Ejecutar contenedores
```shell
docker run --rm -p 8093:8093 --name mock-service-v1  miguelarmasabt/mock-service:v1.0.1
docker run --rm -p 8094:8094 --name token-management-v1  miguelarmasabt/token-management:v1.0.1
```

## ▶️ Kubernetes

⚙️ Crear namespace
```shell
kubectl create namespace security
```

⚙️ Aplicar manifiestos
```shell
kubectl apply -f ./mock-service-v1/k8s-mock-service-v1.yaml -n security
kubectl apply -f ./token-management-v1/k8s-token-management-v1.yaml -n security
```

⚙️ Eliminar orquestación
```shell
kubectl delete -f ./mock-service-v1/k8s-mock-service-v1.yaml -n security
kubectl delete -f ./token-management-v1/k8s-token-management-v1.yaml -n security
```

⚙️ Port-forward
```shell
kubectl port-forward <pod-id-mock-service> 8093:8093 -n security
kubectl port-forward <pod-id-token-management> 8094:8094 -n security
```