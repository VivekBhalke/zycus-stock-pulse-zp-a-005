import React, { useState } from 'react';
import { useProducts, useProcessOrder, useUpdateStock } from '../hooks/useProducts';
import { Product, ProductStatus, TriggerReason } from '../types';
import TriggerBadge from './TriggerBadge';

const Dashboard: React.FC = () => {
  const [selectedCategory, setSelectedCategory] = useState<string>('');
  const { data: products, isLoading, error } = useProducts(undefined, selectedCategory || undefined);
  const processOrderMutation = useProcessOrder();
  const updateStockMutation = useUpdateStock();

  // Filter products that need review or have pending suggestions
  const filteredProducts = products?.filter(product => 
    product.status === ProductStatus.PRICE_REVIEW_PENDING || 
    product.stockLevel < product.reorderThreshold
  ) || [];

  const handleSimulateSale = (productId: string, currentStock: number) => {
    if (currentStock > 0) {
      processOrderMutation.mutate({ id: productId, quantity: 1 });
    }
  };

  const handleUpdateStock = (productId: string, newStockLevel: number) => {
    if (newStockLevel >= 0) {
      updateStockMutation.mutate({ id: productId, newStockLevel });
    }
  };

  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  if (error) {
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
              Error loading products. Please try again later.
            </p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col md:flex-row md:items-center md:justify-between">
        <h2 className="text-2xl font-bold text-gray-900">Products Needing Review</h2>
        <div className="mt-4 md:mt-0">
          <label htmlFor="category" className="block text-sm font-medium text-gray-700 mr-2 inline-block">
            Filter by Category:
          </label>
      {filteredProducts.length === 0 ? (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg">
          <div className="px-4 py-5 sm:p-6">
            <h3 className="text-lg leading-6 font-medium text-gray-900">No products need review</h3>
            <div className="mt-2 max-w-xl text-sm textgray-500">
              <p>All products are currently in good standing. Check back later for new suggestions.</p>
            </div>
          </div>
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {filteredProducts.map((product) => (
            <div key={product.id} className="bg-white overflow-hidden shadow rounded-lg divide-y divide-gray-200">
              <div className="px-4 py-5 sm:px-6">
                <div className="flex items-center justify-between">
                  <h3 className="text-lg font-medium text-gray-900">{product.name}</h3>
                  {product.status === ProductStatus.PRICE_REVIEW_PENDING && (
                    <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-yellow-100 text-yellow-800">
                      Price Review Pending
                    </span>
                  )}
                </div>
                <p className="mt-1 text-sm text-gray-500">{product.sku}</p>
                <div className="mt-2">
                  <TriggerBadge reason={
                    product.stockLevel < product.reorderThreshold 
                      ? TriggerReason.INVENTORY_LOW 
                      : TriggerReason.MANUAL
                  } />
                </div>
              </div>
              <div className="px-4 py-5 sm:p-6">
                <dl className="grid grid-cols-2 gap-4">
                  <div>
                    <dt className="text-sm font-medium text-gray-500">Category</dt>
                    <dd className="mt-1 text-sm text-gray-900">{product.category}</dd>
                  </div>
                  <div>
                    <dt className="text-sm font-medium text-gray-500">Price</dt>
                    <dd className="mt-1 text-sm text-gray-900">${product.currentPrice.toFixed(2)}</dd>
                  </div>
                  <div>
                    <dt className="text-sm font-medium text-gray-500">Stock</dt>
                    <dd className="mt-1 text-sm text-gray-900">{product.stockLevel}</dd>
                  </div>
                  <div>
                    <dt className="text-sm font-medium text-gray-500">Velocity</dt>
                    <dd className="mt-1 text-sm text-gray-900">{product.demandVelocity}/day</dd>
                  </div>
                </dl>
              </div>
              <div className="px-4 py-4 bg-gray-50 sm:px-6">
                <div className="flex space-x-3">
                  <button
                    onClick={() => handleSimulateSale(product.id, product.stockLevel)}
                    className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    Simulate Sale
                  </button>
                  <button
                    onClick={() => handleUpdateStock(product.id, Math.max(0, product.stockLevel - 5))}
                    className="inline-flex items-center px-3 py-2 border border-gray-300 text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    Reduce Stock
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
          <select
            id="category"
            value={selectedCategory}
            onChange={(e) => setSelectedCategory(e.target.value)}
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
          >
            <option value="">All Categories</option>
            <option value="ELECTRONICS">Electronics</option>
            <option value="APPAREL">Apparel</option>
            <option value="HOME">Home</option>
          </select>
        </div>
      </div>