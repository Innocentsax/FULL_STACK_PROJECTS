import "../Component/css/TaskConfirmation.css";

function TaskConfirmation(props) {
    return (
        <div className="task-confirmation-task-confirmation" style={props.style}>
            <div className="task-confirmation-rectangle-4x">
                <p className="task-confirmation-are-you-sure-you-wan">
                    Are you sure you want to create this task ?
                    <br />
                    50,000 will be deducted from your wallet balance to escrow.
                </p>
                <div className="task-confirmation-containing_box_3x">
                    <p className="task-confirmation-task-confirmation-1">
                        Task confirmation
                    </p>
                    <div className="task-confirmation-containing_box_1x">
                        <div className="task-confirmation-frame-8904x">
                            <div className="task-confirmation-frame-7632x">
                                <p className="task-confirmation-yes">Yes</p>
                            </div>
                        </div>
                        <div className="task-confirmation-frame-7633x">
                            <p className="task-confirmation-no">No</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

TaskConfirmation.defaultProps = {
    className: "",
    style: {},
};

export default TaskConfirmation;