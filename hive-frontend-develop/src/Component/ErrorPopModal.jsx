import ErrorIcon from "../Assets/ErrorIcon";

function ErrorPopModal(props) {
    return (
        <div className="success-pop-modal-success-pop-modal" style={props.style}>
            <div className="success-pop-modal-frame-8405x">
               <ErrorIcon />
                <div className="success-pop-modal-frame-8404x">
                    <p className="success-pop-modal-task-createdtask-cre">
                        {props.myProp}
                    </p>
                    <div className="success-pop-modal-frame-8345x">
                        <div className="success-pop-modal-frame-8344x" />
                    </div>
                </div>
            </div>
        </div>
    );
}

ErrorPopModal.defaultProps = {
    className: "",
    style: {},
};

export default ErrorPopModal;
