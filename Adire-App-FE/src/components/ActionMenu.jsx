import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { DotIcon } from '../utils/SVGIcons';
import { useDispatch } from 'react-redux';
import { deleteCustomer, setEditCustomer } from '../features/customer/customerSlice'
import { Link, useNavigate } from 'react-router-dom';
import { EditIcon, DeleteIcon } from '../utils/SVGIcons'

export default function ActionMenu({ id, address, email, firstName, lastname, phoneNumber }) {
    const navigate = useNavigate()
    console.log(address, email, firstName);
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
                    dispatch(setEditCustomer({ editCustomerId: id, address, email, firstName, lastName: lastname, phoneNumber }))
                    navigate('/add-customers')
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
                    dispatch(deleteCustomer(id))
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