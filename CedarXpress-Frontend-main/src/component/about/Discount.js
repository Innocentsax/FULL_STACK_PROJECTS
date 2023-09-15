import React,{Fragment} from "react";

const Discount = (props) => {
    const {classes} = props;
    return(
        <Fragment>
            <div className={classes.get_discount_info}>
            <div className={classes.get_discount_info_header}>
                <h2>Get Discount Info</h2>
                
            </div>
            <div className={classes.get_discount_info_body}>
                <p>Subscribe to the mailing list to receive updates on new arrivals, special offers and other discount information. </p>
            </div>
            <div className={classes.mail_subscription_form}>
                <input type="text" name="" id="" placeholder="Your Email address"/>
                <button>Subscribe <i class="fa fa-long-arrow-right"></i></button>
            </div>
            </div>
        </Fragment>
    )
}

export default Discount;