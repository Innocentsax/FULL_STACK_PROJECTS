import Wrapper from '../assets/wrappers/StatItem';

const StatItem = ({ count, title, icon, color, bcg, item }) => {
  return (
    <Wrapper color={color} bcg={bcg}>
      <header>
        <span className='count'>{item === 'profit' ? `${count}` : count}</span>
        <p className='title'>{title}</p>
      </header>
        <span className='icon'>{icon}</span>
    </Wrapper>
  );
};
export default StatItem;