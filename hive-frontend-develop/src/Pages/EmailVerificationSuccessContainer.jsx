import {useLocation} from 'react-router-dom';
import EmailVerificationSuccessPage from './EmailVerificationSuccessPage';

const EmailVerificationSuccessContainer = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token');
    return < EmailVerificationSuccessPage token={token}/>
};
export default EmailVerificationSuccessContainer;