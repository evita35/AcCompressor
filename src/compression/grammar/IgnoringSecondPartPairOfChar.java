/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.grammar;

import org.leibnizcenter.cfg.token.Token;

import java.util.Objects;

/**
 * value-based class for pairs of characters
 */
public final class IgnoringSecondPartPairOfChar {
    private final char pry;//character for primary sequence
    private final char sec;//character for secondary structure string

    public IgnoringSecondPartPairOfChar(final char pry, final char sec) {
        this.pry = pry;
        this.sec = sec;
    }

    public char getPry() {
        return pry;
    }

    public char getSec() {
        return sec;
    }

    public IgnoringSecondPartPairOfCharTerminal asTerminal() {
        return new IgnoringSecondPartPairOfCharTerminal(this);
    }

    public Token<IgnoringSecondPartPairOfChar> asToken() {
        return Token.of(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IgnoringSecondPartPairOfChar that = (IgnoringSecondPartPairOfChar) o;
        return Character.toLowerCase(pry) == Character.toLowerCase(that.pry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pry);
    }

    @Override
    public String toString()
    {
        return "<"+pry+"|"+sec+">";
    }

}
