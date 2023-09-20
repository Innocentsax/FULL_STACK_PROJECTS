import MainBody  from './Proccess/MainBody'
import Sidebar  from './Proccess/Sidebar'

import { Flex} from "../../Styled/Styled";
const User =()=>{
   


    return (
        <>
       
        <Flex>
        <Sidebar />
        <MainBody />
        </Flex>
        </>
    )
}

export default User;