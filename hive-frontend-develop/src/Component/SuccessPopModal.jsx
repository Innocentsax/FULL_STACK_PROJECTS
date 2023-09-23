import Success from "../Assets/SuccessIcon";
import "../Component/css/SuccessPopModal.css";

function SuccessPopModal(props) {
    return (
        <div className="success-pop-modal-success-pop-modal" style={props.style}>
            <div className="success-pop-modal-frame-8405x">
                <Success />
                <div className="success-pop-modal-frame-8404x">
                    <p className="success-pop-modal-task-createdtask-cre">
                        {props.myProp}
                    </p>
                    <p className="success-pop-modal-task-createdtask-cre">
                        {props.message}
                    </p>
                    <div className="success-pop-modal-frame-8345x">
                        <div className="success-pop-modal-frame-8344x" />
                    </div>
                </div>
            </div>
        </div>
    );
}

SuccessPopModal.defaultProps = {
    className: "",
    style: {},
};

export default SuccessPopModal;
