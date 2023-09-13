import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { DotIcon } from '../utils/SVGIcons';
import { useDispatch } from 'react-redux';
import { deleteOrder, setEditOrder } from '../features/order/orderSlice'
import { Link, useNavigate } from 'react-router-dom';
import { EditIcon, DeleteIcon } from '../utils/SVGIcons'
import { convertMeasurementListToObject } from '../utils/helpers'

export default function OrderActionMenu({ 
    id, 
    customerEmail,
    designImageUrl,
    dueDate,
    duration,
    materialType,
    measurementList,
    orderPrice,
    orderStatus 
}) {
    const navigate = useNavigate()
    console.log({id, 
        customerEmail,
        designImageUrl,
        dueDate,
        duration,
        materialType,
        measurementList,
        orderPrice,
        orderStatus });
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const dispatch = useDispatch();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <div>
        <Button
            id="basic-button"
            aria-controls={open ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
            onClick={handleClick}
        >
            <DotIcon />
        </Button>
        <Menu
            id="basic-menu"
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            MenuListProps={{
            'aria-labelledby': 'basic-button',
            }}
        >
            {/* <Link> */}
            <MenuItem
                onClick={() => {
                    handleClose();
                    dispatch(setEditOrder({ editOrderId: id, customerEmail, designImageUrl, dueDate, duration, materialType, measurementList: convertMeasurementListToObject(measurementList), orderPrice, orderStatus }))
                    navigate('/orders')
                }}
            >
                {/* <Link
                    to={'/add-customers'}
                    onClick={() => {
                        handleClose();
                        // dispatch(setEditCustomer({ editCustomer: id, address, email, firstName, lastName: lastname, phoneNumber }))
                    }}
                > */}
                <span className='icon-grid'>
                    <EditIcon />
                    Edit
                </span>
                {/* </Link> */}
            </MenuItem>
            {/* </Link> */}
            <MenuItem
                onClick={() => {
                    handleClose();
                    dispatch(deleteOrder(id))
                }}
            >
                <span className='icon-grid'>
                    <DeleteIcon />
                    Delete
                </span>
            </MenuItem>
            {/* <MenuItem onClick={handleClose}>Logout</MenuItem> */}
        </Menu>
        </div>
    );
}