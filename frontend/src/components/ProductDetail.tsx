import React from 'react';
import { useParams } from 'react-router-dom';
import { useProduct, useSuggestPricing, useSuggestReorder, useUpdatePricingSuggestion, useUpdateReorderSuggestion } from '../hooks/useProducts';
import { SuggestionStatus, TriggerReason } from '../types';
import TriggerBadge from './TriggerBadge';

const ProductDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data: product, isLoading, error } = useProduct(id || '');
  const suggestPricingMutation = useSuggestPricing();
  const suggestReorderMutation = useSuggestReorder();
  const updatePricingSuggestionMutation = useUpdatePricingSuggestion();
  const updateReorderSuggestionMutation = useUpdateReorderSuggestion();

  const handleAcceptPricing = (suggestionId: number) => {
    updatePricingSuggestionMutation.mutate({ id: suggestionId, status: SuggestionStatus.APPROVED });
  };

  const handleRejectPricing = (suggestionId: number) => {
    updatePricingSuggestionMutation.mutate({ id: suggestionId, status: SuggestionStatus.REJECTED });
  };

  const handleAcceptReorder = (suggestionId: number) => {
    updateReorderSuggestionMutation.mutate({ id: suggestionId, status: SuggestionStatus.APPROVED });
  };

  const handleRejectReorder = (suggestionId: number) => {
    updateReorderSuggestionMutation.mutate({ id: suggestionId, status: SuggestionStatus.REJECTED });
  };

  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="bg-red-50 border-l-4 border-red-400 p-4">
        <div className="flex">
          <div className="flex-shrink-0">
            <svg className="h-5 w-5 text-red-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
            </svg>
          </div>
          <div className="ml-3">
            <p className="text-sm text-red-700">
              Error loading product details. Please try again later.
            </p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="bg-white shadow overflow-hidden sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Product Information</h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">Details and suggestions for {product.name}</p>
        </div>
        <div className="border-t border-gray-200 px-4 py-5 sm:p-0">
          <dl className="sm:divide-y sm:divide-gray-200">
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Name</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.name}</dd>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">SKU</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.sku}</dd>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Category</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.category}</dd>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Current Price</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">${product.currentPrice.toFixed(2)}</dd>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Stock Level</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.stockLevel}</dd>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Reorder Threshold</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.reorderThreshold}</dd>
      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <div className="flex justify-between items-center">
            <h3 className="text-lg leading-6 font-medium text-gray-900">Pricing Suggestions</h3>
            <button
              onClick={() => suggestPricingMutation.mutate(product.id)}
              disabled={suggestPricingMutation.isLoading}
              className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
            >
              {suggestPricingMutation.isLoading ? 'Generating...' : 'Generate Pricing Suggestion'}
            </button>
          </div>
          <div className="mt-4 border-t border-gray-200">
            <div className="pt-4">
              <div className="flex items-center justify-between">
                <div>
                  <h4 className="text-md font-medium text-gray-900">Suggested Price: $29.99</h4>
                  <div className="mt-1">
                    <TriggerBadge reason={TriggerReason.MANUAL} />
                  </div>
                </div>
                <div className="flex space-x-2">
                  <button
                    onClick={() => handleAcceptPricing(1)}
                    className="inline-flex items-center px-3 py-1 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                  >
                    Accept
                  </button>
                  <button
                    onClick={() => handleRejectPricing(1)}
                    className="inline-flex items-center px-3 py-1 border border-gray-300 text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    Reject
                  </button>
                </div>
              </div>
              <div className="mt-2">
                <p className="text-sm text-gray-500">
                  Confidence: 85%
                </p>
      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <div className="flex justify-between items-center">
            <h3 className="text-lg leading-6 font-medium text-gray-900">Reorder Suggestions</h3>
            <button
              onClick={() => suggestReorderMutation.mutate(product.id)}
              disabled={suggestReorderMutation.isLoading}
              className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
            >
              {suggestReorderMutation.isLoading ? 'Generating...' : 'Generate Reorder Suggestion'}
            </button>
          </div>
          <div className="mt-4 border-t border-gray-200">
            <div className="pt-4">
              <div className="flex items-center justify-between">
                <div>
                  <h4 className="text-md font-medium text-gray-900">Suggested Quantity: 50 units</h4>
                  <div className="mt-1">
                    <TriggerBadge reason={TriggerReason.MANUAL} />
                  </div>
                </div>
                <div className="flex space-x-2">
                  <button
                    onClick={() => handleAcceptReorder(1)}
                    className="inline-flex items-center px-3 py-1 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                  >
                    Accept
                  </button>
                  <button
                    onClick={() => handleRejectReorder(1)}
                    className="inline-flex items-center px-3 py-1 border border-gray-300 text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    Reject
                  </button>
                </div>
              </div>
              <div className="mt-2">
                <p className="text-sm text-gray-500">
                  Confidence: 92%
                </p>
                <p className="mt-1 text-sm text-gray-700">
                  Current stock level is approaching reorder threshold. Recommended quantity accounts for lead time and expected demand over the next 30 days.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
                <p className="mt-1 text-sm text-gray-700">
                  Based on current market trends and inventory levels, increasing the price would optimize revenue while maintaining competitive positioning.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Demand Velocity</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">{product.demandVelocity}/day</dd>
            </div>
            <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Status</dt>
              <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                  {product.status}
                </span>
              </dd>
            </div>
          </dl>
        </div>
      </div>