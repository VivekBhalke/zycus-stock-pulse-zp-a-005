import { useQuery, useMutation, useQueryClient } from 'react-query';
import { 
  getProducts, 
  getProductById, 
  createProduct, 
  updateStock, 
  processOrder,
  suggestPricing,
  updatePricingSuggestion,
  suggestReorder,
  updateReorderSuggestion
} from '../services/api';
import { 
  Product, 
  ProductStatus, 
  SuggestionStatus 
} from '../types';

// Product Queries
export const useProducts = (status?: ProductStatus, category?: string) => {
  return useQuery(['products', status, category], () => getProducts(status, category));
};

export const useProduct = (id: string) => {
  return useQuery(['product', id], () => getProductById(id));
};

// Product Mutations
export const useCreateProduct = () => {
  const queryClient = useQueryClient();
  return useMutation(createProduct, {
    onSuccess: () => {
      queryClient.invalidateQueries('products');
    },
  });
};

export const useUpdateStock = () => {
  const queryClient = useQueryClient();
  return useMutation(({ id, newStockLevel }: { id: string; newStockLevel: number }) => 
    updateStock(id, newStockLevel), {
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries(['product', variables.id]);
      queryClient.invalidateQueries('products');
    },
  });
};

export const useProcessOrder = () => {
  const queryClient = useQueryClient();
  return useMutation(({ id, quantity }: { id: string; quantity: number }) => 
    processOrder(id, quantity), {
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries(['product', variables.id]);
      queryClient.invalidateQueries('products');
    },
  });
};

// Pricing Suggestion Mutations
export const useSuggestPricing = () => {
  return useMutation(suggestPricing);
};

export const useUpdatePricingSuggestion = () => {
  const queryClient = useQueryClient();
  return useMutation(({ id, status }: { id: number; status: SuggestionStatus }) => 
    updatePricingSuggestion(id, status), {
    onSuccess: () => {
      queryClient.invalidateQueries('products');
    },
  });
};

// Reorder Suggestion Mutations
export const useSuggestReorder = () => {
  return useMutation(suggestReorder);
};

export const useUpdateReorderSuggestion = () => {
  const queryClient = useQueryClient();
  return useMutation(({ id, status }: { id: number; status: SuggestionStatus }) => 
    updateReorderSuggestion(id, status), {
    onSuccess: () => {
      queryClient.invalidateQueries('products');
    },
  });
};