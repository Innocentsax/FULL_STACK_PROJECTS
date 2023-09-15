import React,{Fragment} from "react";

const FindYourFurniture = (props) => {

    const {classes} = props;
    return (
        <Fragment>
                    <div className={classes.find_your_furnture}>
            <div className={classes.find_your_furniture_top}>
                <div className={classes.find_your_furniture_top_left}>
                    <div className={classes.find_your_furniture_top_left_header}>
                        <h2>FIND YOUR BEST FURNITURE</h2>
                    </div>
                    <div className={classes.find_your_furniture_top_left_body}>
                        <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics</p>
                    </div>
                    <div className={classes.find_your_furniture_top_left_body_button}>
                        <button>SHOP NOW <i class="fa fa-long-arrow-right"></i></button>
                    </div>
                </div>
                <div className={classes.find_your_furniture_top_right}>
                    <p>Modern Furniture</p>
                    <div className={classes.bar}></div>
                    <p>Classic Furniture</p>
                    <div className={classes.bar}></div>
                    <p>Wooden Furniture</p>
                    <div className={classes.bar}></div>
                </div>
            </div>
            <div className={classes.find_your_furniture_bottom}>
                <div className={classes.find_your_furniture_bottom_left}>
                    <div className={classes.find_your_furniture_bottom_left_left}>
                        <div className={classes.icon}>
                            <i class="fa fa-truck"></i>
                        </div>
                        <div className={classes.icon_text}>
                            <p>Free Shipping</p>
                            <p>On order over $500</p> 
                        </div>
                    </div>
                    <div className={classes.find_your_furniture_bottom_right_left}>
                        <div className={classes.icon}>
                            <i class="fa fa-cc-visa"></i>
                        </div>
                        <div className={classes.icon_text}>
                            <p>Quick Payment</p>
                            <p>100% Secure Payment </p>
                        </div>
                    </div>
                </div>
                <div className={classes.find_your_furniture_bottom_right}>
                    <div className={classes.find_your_furniture_bottom_right_left}>
                        <div className={classes.icon}>
                            <i class="fa fa-truck"></i>
                        </div>
                        <div className={classes.icon_text}>
                            <p>Free Shipping</p>
                            <p>On order over $500</p>
                        </div>
                    </div>
                    <div className={classes.find_your_furniture_bottom_right_left}>
                        <div className={classes.icon}>
                            <i class="fa fa-cc-visa"></i>
                        </div>
                        <div className={classes.icon_text}>
                            <p>Quick Payment</p>
                            <p>100% Secure Payment </p>
                        </div>
                    </div>
                </div>

                
            </div>
        </div>
        </Fragment>
    );
}

export default FindYourFurniture;