import React,{Fragment} from "react";


const AboutHero = (props) => {

    const {classes,signature,mainimage } = props;
    return (
        <Fragment>
            <div className={classes.main_about_us_image}>
            <img src={mainimage} alt="Image"/>
            </div>
            <div className={classes.history_of_furniture}>
                <div className={classes.history_header}>
                    <h2>History About Furniture</h2>
                </div>
                <div className={classes.history_body}>
                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic </p>
                </div>
                <div className={classes.history_signature}>
                    <img src={signature} alt="Signature"/>
                </div>
            </div>
        </Fragment>
    );
}
export default AboutHero;