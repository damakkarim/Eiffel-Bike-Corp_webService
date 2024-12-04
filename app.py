from fastapi import FastAPI
from pydantic import BaseModel
from transformers import pipeline
from typing import List

# Initialize FastAPI app
app = FastAPI()

# Load a zero-shot classification pipeline
classifier = pipeline("zero-shot-classification", model="facebook/bart-large-mnli")

# Define possible conditions
labels = ["Good condition", "Needs minor repairs", "Needs major repairs"]

# Request body schema
class BikeDescriptions(BaseModel):
    descriptions: List[str]  # A list of user descriptions (notes)

# Route for predicting bike condition based on combined notes
@app.post("/predict-condition")
def predict_condition(data: BikeDescriptions):
    # Combine all descriptions into one string
    combined_text = " ".join(data.descriptions)
    
    # Predict the condition for the combined text
    result = classifier(combined_text, labels)
    
    # Return the highest-confidence condition
    return {
        "predicted_condition": result['labels'][0]
    }

# Root endpoint (optional)
@app.get("/")
def read_root():
    return {"message": "Bike Condition Prediction API"}
