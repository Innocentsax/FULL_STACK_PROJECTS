export const getDueDate = (numberOfDays) => {
    const currentDate = new Date();
    const dueDate = new Date(currentDate.getTime() + numberOfDays * 24 * 60 * 60 * 1000).toISOString();
    return dueDate;
} 