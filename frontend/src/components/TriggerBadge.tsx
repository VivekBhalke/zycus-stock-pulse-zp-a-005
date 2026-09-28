import React from 'react';
import { TriggerReason } from '../types';

interface TriggerBadgeProps {
  reason: TriggerReason;
}

const TriggerBadge: React.FC<TriggerBadgeProps> = ({ reason }) => {
  const getBadgeStyle = () => {
    switch (reason) {
      case TriggerReason.INVENTORY_LOW:
        return 'bg-red-100 text-red-800';
      case TriggerReason.DEMAND_SPIKE:
        return 'bg-yellow-100 text-yellow-800';
      case TriggerReason.MANUAL:
        return 'bg-blue-100 text-blue-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const getLabelText = () => {
    switch (reason) {
      case TriggerReason.INVENTORY_LOW:
        return 'Inventory Low';
      case TriggerReason.DEMAND_SPIKE:
        return 'Demand Spike';
      case TriggerReason.MANUAL:
        return 'Manual Request';
      default:
        return reason;
    }
  };

  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getBadgeStyle()}`}>
      {getLabelText()}
    </span>
  );
};

export default TriggerBadge;