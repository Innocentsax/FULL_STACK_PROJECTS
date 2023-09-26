import {useState} from "react";
import Select, { components } from 'react-select';

const { Option } = components;

const CustomOption = (props) => {
    if (props.data.isHeading) {
        return (
            <Option {...props}>
                <div className="h-10 py-2.5 px-6 bg-slate-200 font-semibold m-0 text-sm">
                    {props.data.label}
                </div>
            </Option>
        );
    }

    return (
        <Option {...props}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <img
                    src={props.data.imageSrc}
                    alt={props.data.label}
                    className="w-11 h-8 rounded-full mx-6"
                    // style = {{ width: '45px', height: '34px', margin: '0 24px', borderRadius: '50%' }}
                />
                <div className="m-0">
                    {props.data.label}
                </div>
            </div>
        </Option>
    );
}

const InternetPayments = () => {
    const [selectedServiceProvider, setSelectedServiceProvider] = useState('');
    // const [isModalOpen, setIsModalOpen] = useState(false);
    const [showPackageDropdown, setShowPackageDropdown] = useState(false);
    const [selectedDataBundle, setSelectedDataBundle] = useState(null);

    const serviceProviderOptions = [
        { value: 'heading', label: 'Select Option', isHeading: true },
        { value: 'mtn-data', label: 'MTN Data' },
        { value: 'glo-data', label: 'Glo Data' },
        { value: 'airtel-data', label: 'Airtel Data'},
        { value: '9mobile-data', label: '9Mobile Data'}
    ];

    const dataBundleOptions = {
        'mtn-data': [
            { value: 'mtn-data-500mb', label: 'MTN Data / 500MB',},
            { value: 'mtn-data-1gb', label: 'MTN Data / 1GB'},
            { value: 'mtn-data-4gb', label: 'MTN Data / 4GB'},
            { value: 'mtn-data-6gb', label: 'MTN Data / 6GB' },
            { value: 'mtn-data-20gb', label: 'MTN Data / 20GB'}
        ],
        'glo-data': [
            { value: 'glo-data-500mb', label: 'Glo Data / 500MB' },
            { value: 'glo-data-1gb', label: 'Glo Data / 1GB' },
            { value: 'glo-data-4gb', label: 'Glo Data / 4GB'},
            { value: 'glo-data-6gb', label: 'Glo Data / 6GB' },
            { value: 'glo-data-20gb', label: 'Glo Data / 20GB' }
        ],
        'airtel-data': [
            { value: 'airtel-data-500mb', label: 'Airtel Data / 500MB' },
            { value: 'airtel-data-1gb', label: 'Airtel Data / 1GB' },
            { value: 'airtel-data-4gb', label: 'Airtel Data / 4GB' },
            { value: 'airtel-data-6gb', label: 'Airtel Data / 6GB' },
            { value: 'airtel-data-20gb', label: 'Airtel Data / 20GB' }
        ],
        '9mobile-data': [
            { value: '9mobile-data-500mb', label: '9Mobile Data / 500MB' },
            { value: '9mobile-data-1gb', label: '9Mobile Data / 1GB' },
            { value: '9mobile-data-4gb', label: '9Mobile Data / 4GB' },
            { value: '9mobile-data-6gb', label: '9Mobile Data / 6GB' },
            { value: '9mobile-data-10gb', label: '9Mobile Data / 20GB' }
        ]
    };

    const handleServiceProviderOptionChange = (selectedProvider) => {
        setSelectedServiceProvider(selectedProvider);
        setSelectedDataBundle(null); // Reset the selected data bundle when the service provider changes
        setShowPackageDropdown(true); // Show the additional dropdown when an option is selected
    };

    const handleDataBundleOptionChange = (selectedDataBundle) => {
        setSelectedDataBundle(selectedDataBundle);
    };

    return (
        <div className = "bg-gray-100 h-[100%] w-[100%] flex justify-center pt-[40px]">
            <div className = "max-w-[580px] w-[90%] bg-gray-100">
                <h2 className = "mb-8 text-2xl font-bold text-center">Internet</h2>
                {/*div containing select provider card* (select provider dropdown)*/}
                <div className = "min-h-fit w-[100%] p-8 bg-white">
                    {/*div with dropdown and label*/}
                    <div className = "flex flex-col">
                        <div>
                            <label htmlFor="select-provider-dropdown" className="text-sm font-semibold">
                                Select Provider
                            </label>
                        </div>
                        {/*dropdown menu*/}
                        <div>
                            <Select
                                id="select-provider-dropdown"
                                value={selectedServiceProvider}
                                onChange={handleServiceProviderOptionChange}
                                options={serviceProviderOptions}
                                components={{
                                    Option: CustomOption,
                                }}
                            />
                        </div>
                    </div>

                    {/*div containing show package dropdown*/}

                    {showPackageDropdown && selectedServiceProvider && (
                        <div className = "mt-4">
                            {/*<div className="h-335 w-697 p-8 bg-white mt-4">*/}
                            <div className="flex flex-col">
                                <div>
                                    <label htmlFor="show-package-dropdown" className="text-sm font-semibold">
                                        Package
                                    </label>
                                </div>
                                {/* Dropdown menu for selecting the data bundle */}
                                <div>
                                    <Select
                                        id="show-package-dropdown"
                                        value={selectedDataBundle}
                                        onChange={handleDataBundleOptionChange}
                                        options={[
                                            { value: 'heading', label: 'Select Option', isHeading: true },
                                            ...(dataBundleOptions[selectedServiceProvider.value] || []) // Retrieve the data bundle options based on the selected service provider
                                        ]}
                                        components={{
                                            Option: CustomOption,
                                        }}
                                    />
                                </div>
                            </div>

                            {selectedDataBundle && ( // Check if a package is selected
                                <div>
                                    <div className="mb-4">
                                        <div className="mb-4 mt-4">
                                            <label className="block text-sm font-semibold text-gray-700 mb-1">Phone Number</label>
                                            <input
                                                type="text"
                                                placeholder="09034885493"
                                                className="border border-gray-300 rounded-md px-3 py-2 w-full"
                                            />
                                        </div>
                                        <div className="mb-4">
                                            <label className="block text-sm font-semibold text-gray-700 mb-1">Amount</label>
                                            <input
                                                type="text"
                                                placeholder="2000"
                                                className="border border-gray-300 rounded-md px-3 py-2 w-full"
                                            />
                                        </div>
                                        <div className="mb-4">
                                            <button className="bg-indigo-700 hover:bg-indigo-800 text-white rounded-md px-4 py-2 w-full">
                                                Continue
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default InternetPayments;