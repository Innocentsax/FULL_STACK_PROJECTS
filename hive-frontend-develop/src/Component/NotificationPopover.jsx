import * as React from 'react';
import Popover from '@mui/material/Popover';
import Typography from '@mui/material/Typography';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell } from "@fortawesome/free-regular-svg-icons";
import Button from '@mui/material/Button';
import NotificationBox from "./NotificationBox/NotificationBox";

export default function NotificationPopover({ notify }) {
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(anchorEl ? null : event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const open = Boolean(anchorEl);
    const id = open ? 'simple-popover' : undefined;

    return (
        <div>
            <FontAwesomeIcon
                aria-describedby={id}
                variant="contained"
                onClick={handleClick}
                icon={faBell}
            />
            <Popover
                id={id}
                open={open}
                anchorEl={anchorEl}
                onClose={handleClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
            >
                {notify.map((notification) => (
                    <NotificationBox
                        key={notification.createdAt}
                        title={notification.title}
                        body={notification.body}
                        elapsedTime={notification.elapsedTime}
                    />
                ))}
            </Popover>
        </div>
    );
}
