import React from 'react'
import { useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';
import Box from '@mui/material/Box';
import {Modal, Typography} from "@mui/material";
import '../Signup/signup.css';
const CreateAddress = () => {
    const { register, handleSubmit, formState: {errors} } = useForm()
    const {uname} = useParams();
    console.log(uname)
    //Modal
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const style = {
        position: 'absolute',
        fontfamily: 'sans-serif',
        borderRadius: '2em',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        backgroundColor: 'white',
        color:'black',
        boxShadow: 24,
        p: 4,
    };
    const onSubmit = async (data) =>{
        console.log(data)
        let res = await fetch("https://fitnesso-app-new.herokuapp.com/address/addAddress", {
        // let res = await fetch("http://localhost:9067/address/addAddress", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
        let info = await res.json();
        console.log(info);
        let received = info.message;
        console.log("message: ", received)
    }
    return (
        <div className="login-container">
            <div className="addressform">
                <div className={"addresstop"}>
                    <h3 className="addresstitle">Address</h3>
                    <button className={"goback"}><a href={"/signup"}>X</a></button>
                </div>
                <form className='form12' onSubmit={handleSubmit(onSubmit)}>
                    <input type={'text'} className="user1" align="center"
                           name='userName' value={uname} placeholder='UserName' {...register('userName', { required: true})}/>
                    <input type="text" className='inputers2' align="center"
                           placeholder='Street Detail' name='streetDetail' {...register('streetDetail', { required: true})}/>
                    <div className={"errors"}>
                        <h6 >{errors.streetDetail && <span>street Detail is required</span>}</h6>
                    </div>
                    <input type="text" className='inputers2' align="center"
                           placeholder='State' name='state' {...register('state', { required: true })}/>
                    <div className={"errors"}>
                        <h6 >{errors.state && <span>state is required</span>}</h6>
                    </div>
                    <input type="text" className='inputers2' align="center"
                           placeholder='City' name='city' {...register('city', { required: true })}/>
                    <div className={"errors"}>
                        <h6 >{errors.city && <span>city is required</span>}</h6>
                    </div>
                    <input name='zipCode'  type="text" className='inputers2' align="center"
                           placeholder='Zip Code' {...register('zipCode', { required: true })}/>
                    <div className={"errors"}>
                        <h6 >{errors.zipCode && <span>Zip Code is required</span>}</h6>
                    </div>
                    <input name='country'  type="text" className='inputers2' align="center"
                           placeholder='Country' {...register('country', { required: true })}/>
                    <div className={"errors"}>
                        <h6 >{errors.country && <span>Country is required</span>}</h6>
                    </div>
                    <div className="fromgender">
                        <button type='submit' className={"sender"} onClick={handleOpen}>Add Address</button>
                    </div>
                </form>
            </div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography className={'modalbody'} id="modal-modal-description" sx={{ mt: 2 }}>
                        {uname} Please Verify your account through your email to complete registration
                    </Typography>
                    <button className={"modalcontinue"}><a href="/login" >Continue</a></button>
                </Box>
            </Modal>
        </div>
    )
}
export default CreateAddress