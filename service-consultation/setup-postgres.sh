#!/bin/bash
echo "🗄️  CONFIGURATION POSTGRESQL POUR SERVICE CONSULTATION"

echo "1. Création de la base de données..."
sudo -u postgres psql -c "CREATE DATABASE consultation_db;" 2>/dev/null || echo "⚠️  La base existe peut-être déjà"

echo "2. Création de l'utilisateur..."
sudo -u postgres psql -c "CREATE USER consultation_user WITH PASSWORD 'consultation_pass';" 2>/dev/null || echo "⚠️  L'utilisateur existe peut-être déjà"

echo "3. Attribution des privilèges..."
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE consultation_db TO consultation_user;" 2>/dev/null
sudo -u postgres psql -c "ALTER USER consultation_user CREATEDB;" 2>/dev/null

echo "4. Vérification de la connexion..."
# Utiliser PGPASSWORD pour éviter la demande interactive
PGPASSWORD=consultation_pass psql -h localhost -U consultation_user -d consultation_db -c "\q" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Configuration PostgreSQL réussie !"
    echo ""
    echo "📊 Informations de connexion:"
    echo "   Host: localhost:5432"
    echo "   Database: consultation_db"
    echo "   User: consultation_user"
    echo "   Password: consultation_pass"
else
    echo "❌ Problème de connexion"
    echo "🔧 Vérification manuelle nécessaire"
fi
