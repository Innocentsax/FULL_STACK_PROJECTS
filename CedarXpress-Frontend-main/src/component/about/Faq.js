import React,{Fragment} from "react";

const Faq = (props) => {
    const {classes,bottomimage} = props;
    return(
        <Fragment>
            <div className={classes.frequently_asked_questions}>
            <div className={classes.frequently_asked_questions_left}>
                <img src={bottomimage}/>
            </div>
            <div className={classes.frequently_asked_questions_right}>
                <h2>Frequently Asked Questions</h2>
                <div className={classes.faq_questions}>
                    <p>Which credit cards do you accept?</p>
                    <p>Which credit cards do you accept?</p>
                    <p>How long will it be before I get a refund?</p>
                </div>
            </div>
            </div>
        </Fragment>
    )
}
export default Faq;