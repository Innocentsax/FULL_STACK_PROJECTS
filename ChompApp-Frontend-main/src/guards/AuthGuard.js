import { useSelector } from "react-redux"
import { Navigate } from "react-router-dom";


export const AuthGuard = ({children, roles}) => {
    const currentUser = useSelector(state => state.user);

    const authorize = () => {
        if(!currentUser){
            return (<Navigate to={{pathname: "/login"}} />)
        }

        if ((currentUser.roles[0].name) !== "ROLE_ADMIN") {
          return <Navigate to={{ pathname: "/401" }} />;
        }

        return (children);
    }

    return (authorize());
}