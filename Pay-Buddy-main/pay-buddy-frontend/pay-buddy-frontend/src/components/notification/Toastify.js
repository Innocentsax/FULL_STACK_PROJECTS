import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const notify = (message) => toast(message)
export const notifyError = (message) => toast.error(message)
export const notifySuccess = (message, theme="light", position="top-right") => toast.success(message, {
    position: position,
    theme: theme,
    autoClose: 1000,
})
export const notifyWarning = (message) => toast.warning(message)



// export default Toastify