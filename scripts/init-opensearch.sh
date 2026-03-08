#!/bin/sh

echo "Waiting for OpenSearch to start..."
until curl -s http://opensearch-node:9200 > /dev/null; do
  sleep 5
done
echo "OpenSearch is up and running!"

HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://opensearch-node:9200/products)

if [ "$HTTP_STATUS" -eq 200 ]; then
  echo "Index 'products' already exists. Skipping creation."
else
  echo "Creating 'products' index from mapping file..."
  curl -X PUT "http://opensearch-node:9200/products" \
       -H 'Content-Type: application/json' \
       -d @/scripts/product-index-mapping.json
       
  echo -e "\nInitialization complete!"
fi