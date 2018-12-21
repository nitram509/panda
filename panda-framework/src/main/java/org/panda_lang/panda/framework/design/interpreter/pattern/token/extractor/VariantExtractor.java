package org.panda_lang.panda.framework.design.interpreter.pattern.token.extractor;

import org.panda_lang.panda.framework.design.interpreter.pattern.lexical.elements.LexicalPatternElement;
import org.panda_lang.panda.framework.design.interpreter.pattern.lexical.elements.LexicalPatternNode;
import org.panda_lang.panda.framework.language.interpreter.token.distributors.TokenDistributor;

class VariantExtractor extends AbstractElementExtractor<LexicalPatternNode> {

    protected VariantExtractor(ExtractorWorker worker) {
        super(worker);
    }

    @Override
    public ExtractorResult extract(LexicalPatternNode element, TokenDistributor distributor) {
        if (!element.isVariant()) {
            throw new RuntimeException("The specified node is not marked as a variant node");
        }

        int index = distributor.getIndex();

        for (LexicalPatternElement variantElement : element.getElements()) {
            ExtractorResult result = super.getWorker().extract(distributor, variantElement);

            if (result.isMatched()) {
                return result.identified(variantElement.getIdentifier());
            }

            distributor.setIndex(index);
        }

        return new ExtractorResult("Variant does not matched");
    }

}