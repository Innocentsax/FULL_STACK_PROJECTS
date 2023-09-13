import moment from "moment";
import { statusOptions } from './enumData';

export function convertMeasurementsToMeasurementList(measurements) {
    const measurementList = Object.entries(measurements)
        .filter(([key, value]) => value !== "") // exclude keys with empty values
        .map(([key, value]) => ({
        type: key,
        value: parseFloat(value)
        }));

    return measurementList;
}

export function convertMeasurementListToObject(measurementList) {
    const measurements = {};
    measurementList.forEach((measurement) => {
        measurements[measurement.type] = measurement.value.toString();
    });
    return measurements;
}

export function convertMeasurementListToMeasurements(measurementList) {
    const measurements = measurementList.reduce((obj, { type, value }) => {
      obj[type] = value.toString(); // Convert value to a string and assign to key
        return obj;
    }, {});

    return measurements;
}

export function formatDate(dateString) {
    const date = moment(dateString);
    const formattedDate = date.format("DD/MM/YYYY");
    return formattedDate;
}



export const getOrderStatus = (status) => {
    switch (status) {
      case 'IN_PROGRESS':
        return statusOptions.IN_PROGRESS;
      case 'CANCELLED':
        return statusOptions.CANCELLED;
      case 'PENDING':
        return statusOptions.PENDING;
      case 'COMPLETED':
        return statusOptions.COMPLETED;
      default:
        return status;
    }
  }

  // Alternatively, you can use an object lookup to map the order status:
  // const getOrderStatus = (status) => statusOptions[status] || status;


export function formatMeasurementsToString(measurements) {
    const formattedValues = [];
    for (const [key, value] of Object.entries(measurements)) {
        if (!!value) {
            const formattedKey = key.replace(/_/g, " ");
            formattedValues.push(`${formattedKey} ${value}`);
        }
    }
    return formattedValues.join(", ");
}


export function formatMeasurements(measurements) {
    const formattedList = measurements.map(({ type, value }) => {
        const formattedType = type.toLowerCase().replace(/_/g, ' ');
        const formattedValue = value.toString();
        return `${formattedType.charAt(0).toUpperCase()}${formattedType.slice(1)} ${formattedValue}`;
    });
    return formattedList.join(', ');
}