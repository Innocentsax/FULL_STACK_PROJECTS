import React from "react";


const Breadcrumb = (props) => {
    const{classes} = props;
    return (
        <div className={classes.about_us_header}>
        <div className={classes.about_us_header_title}>
            <h2>ABOUT US</h2>
        </div>
        <div className={classes.about_us_header_breadcrumb}>
            Home  About us
        </div>
    </div>
    );
}
export default Breadcrumb;