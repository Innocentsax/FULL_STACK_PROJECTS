function SuccessModal ({ closeModal })  {

    return (
        <div className="fixed inset-0 flex items-center justify-center z-50">
            <div className="bg-white rounded-lg p-8">
                <div className="flex items-center justify-center mb-6">
                    {/* Success Icon */}
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-8 w-8 text-green-500"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M5 13l4 4L19 7"
                        />
                    </svg>
                </div>

                <h1 className="text-lg font-bold text-center mb-2">Successful</h1>
                <p className="text-center text-gray-600 mb-6">
                    You have successfully applied for this position. Further information will be communicated via email.
                </p>

                <div className="flex justify-center">
                    {/* Close button */}
                    <button
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                        onClick={() => closeModal(false)}
                    >
                        Close
                    </button>
                </div>
            </div>
        </div>

    );
}

export default SuccessModal;