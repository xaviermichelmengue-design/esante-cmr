#!/bin/bash

# Script de déploiement pour le service consultation
NAMESPACE="esante-cmr"
SERVICE_NAME="service-consultation"

echo "Déploiement du service consultation dans le namespace $NAMESPACE"

# Appliquer les configurations
kubectl apply -f secret.yaml
kubectl apply -f configmap.yaml
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f hpa.yaml

# Vérifier le déploiement
echo "Vérification du déploiement..."
kubectl get deployments -n $NAMESPACE | grep $SERVICE_NAME
kubectl get pods -n $NAMESPACE | grep $SERVICE_NAME
kubectl get services -n $NAMESPACE | grep $SERVICE_NAME

echo "Déploiement terminé. Utilisez 'kubectl get pods -n $NAMESPACE' pour vérifier l'état des pods."
