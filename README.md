# quiz-api

## Prerequisites
- Java 21 JDK
- Node.js (version 14 or higher)
- Maven
- Git

## Backend Setup (Java Spring Boot)

### 1. Clone the Repository
```bash
git clone https://github.com/blitzboah/quiz-api.git
cd quiz-api/backend
```

### 2. Build the Backend
```bash
# Using Maven
mvn clean install
```

### 3. Database Configuration
- The application uses H2 in-memory database
- Database configuration is automatically handled in `application.properties`
- Schema is set to auto-update on application startup

### 4. Run the Backend
```bash
# Using Maven
mvn spring-boot:run
```

### Backend API Endpoints
- `GET /api/validate-user`: Validate user access
- `GET /api/question`: Fetch next quiz question
- `POST /api/submit`: Submit answer for a question
- `GET /api/results`: Retrieve quiz results
- `DELETE /api/delete`" Deletes the user session

## Frontend Setup (React)

### 1. Navigate to Frontend Directory
```bash
cd ../quiz-frontend
```

### 2. Install Dependencies
```bash
npm install
```

### 3. Run the Frontend
```bash
npm run dev
```

## Running the Full Application

### Separate Terminal Windows
1. Start Backend: In backend directory, run `mvn spring-boot:run`
2. Start Frontend: In frontend directory, run `npm run dev`

## Application Workflow
1. User enters user ID on login screen
2. Backend validates the user
3. Quiz questions are fetched one at a time
4. User answers questions sequentially
5. After completing quiz, results are displayed
6. Session data is deleted

## Troubleshooting
- Ensure backend is running on port 8080
- Check console for any connection or dependency issues
- Verify Java and Node.js versions match prerequisites

## Technology Stack
- **Backend**: Java 21, Spring Boot
- **Frontend**: React
- **Database**: H2 In-Memory Database
- **Build Tools**: Maven (Backend), npm (Frontend)
