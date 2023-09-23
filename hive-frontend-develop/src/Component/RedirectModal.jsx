import "../Component/css/RedirectModal.css";

function RedirectModal(props) {
    return (
        <div className="redirect-modal-redirect-modal" style={props.style}>
            <div className="redirect-modal-frame-8405x">
                <div className="redirect-modal-image-5x" />
                <div className="redirect-modal-frame-8404x">
                    <p className="redirect-modal-you-will-be-redirect">
                        You will be redirected to the Paystack
                        <br />
                        to complete your payment.
                    </p>
                    <div className="redirect-modal-frame-8345x">
                        <div className="redirect-modal-frame-8344x" />
                    </div>
                </div>
            </div>
        </div>
    );
}

RedirectModal.defaultProps = {
    className: "",
    style: {},
};

export default RedirectModal;
