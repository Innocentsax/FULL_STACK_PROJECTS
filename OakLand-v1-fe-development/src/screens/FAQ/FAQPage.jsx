import { faqData } from '../../faqData'
import FAQ from '../../components/ProductCard/FAQ';

const FAQPage = () => {
  const paymentQuestions = faqData.filter(x => x.questionType === 'payment')
  const faqs = faqData.filter(x => x.questionType === 'faq')

  return (
    <section className="faq-section">
      <FAQ data={faqs} title={"FREQUENTLY ASKED QUESTIONS"} />
      <FAQ data={paymentQuestions} title={"PAYMENT QUESTIONS"} />
    </section>

  );
};

export default FAQPage;