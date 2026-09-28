# StockPulse Merchandising Console

Frontend application for the StockPulse agentic commerce recommendation system.

## Features

- Dashboard view showing products needing review
- Product detail view with pricing and reorder suggestions
- Accept/Reject functionality for suggestions
- Trigger reason badges (Inventory Low, Demand Spike, Manual)
- Product information display (stock level, price, demand velocity)
- Demo controls (simulate sale, adjust stock)

## Tech Stack

- React 18 with TypeScript
- Vite for fast development
- React Query for server state management
- Tailwind CSS for styling
- React Router for navigation

## Getting Started

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm run dev
```

The application will be available at http://localhost:3000

## Project Structure

```
src/
├── components/     # React components
├── hooks/          # Custom React hooks
├── services/       # API service layer
├── types.ts        # TypeScript types
├── App.tsx         # Main app component
└── main.tsx        # Entry point
```

## Development

The frontend is configured to proxy API requests to the backend running on http://localhost:8080.