import { useState } from 'react'
import styles from './DeleteModal.module.css'
import BigCard from '../../UI/BigCard';
import { useNavigate } from 'react-router-dom';
import Button from "../../UI/Button";
import { useParams } from 'react-router-dom';
import { baseEndpoint } from '../../globalresources/Config';
const DeleteModal = (props) => {

   //alert(props.id)
    const token = localStorage.getItem("token");
    const[deleteModal, setDeleteModal]= useState(true);
    const navigate = useNavigate();
    
    const logoutHandler = ()=>{
        console.log()
        deleteBudget()
        navigate('/decapay/budget-list');
    }
    const imageClick = ()=>{
        setDeleteModal(null)
    }
    const {id} = useParams()

    const deleteBudget = async ()=>{
        const req = await fetch(`${baseEndpoint}/api/v1/budgets/${props.id}`,{
        method: 'DELETE',
        headers: { 
            "contentType":"application/json",
        Authorization: `Bearer ${token}`,
        }
    }
        )
        const res = await req.json()
    }
    return ( 
        <>      
          { deleteModal &&      
          <div>              
              <div className={styles.backdrop} onClick={imageClick}></div>             
                 <BigCard className={styles.delete}>            
                     <div>              
                          <p className={styles.title}>Are you sure you want to delete?</p>            
                              </div>                 
                                 <Button onClick={logoutHandler} >Yes</Button>                 
                                    <button className={styles.btns} onClick={imageClick} >No</button>            
                                        </BigCard>      
                                          </div>  
                                               }
        </>   
          );
}
export default DeleteModal;