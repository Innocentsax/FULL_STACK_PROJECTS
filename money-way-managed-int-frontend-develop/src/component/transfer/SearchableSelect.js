import Select from 'react-select';

const options = [
    { value: 'first-bank', label: 'First Bank' },
    { value: 'guaranty-trust-bank', label: 'Guaranty Trust Bank' },
    { value: 'polaris-bank', label: 'Polaris Bank' },
];

const SearchableSelect = () => {
    return (
        <Select
            options={options}
            isSearchable={true}
        />
    );
};

export default SearchableSelect;