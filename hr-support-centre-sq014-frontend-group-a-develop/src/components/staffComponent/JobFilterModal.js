import PropTypes from "prop-types";

function JobFilterModal({ selectedFilter, onFilterChange, closeFilterModal }) {

    const handleFilterChange = (event) => {
        const selectedValue = event.target.value;
        onFilterChange(selectedValue);
        closeFilterModal();
    };

    return (
        <div>
            <select value={selectedFilter} onChange={handleFilterChange}>
                <option value="newest">Newest</option>
                <option value="oldest">Oldest</option>
                <option value="past_week">Past Week</option>
            </select>
        </div>
    );
}
JobFilterModal.propTypes = {
    selectedFilter: PropTypes.string.isRequired,
    onFilterChange: PropTypes.func.isRequired,
    closeFilterModal: PropTypes.func.isRequired,
};

export default JobFilterModal;