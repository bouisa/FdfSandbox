/*
 * Copyright (c) 2012, JFXtras
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jfxtras.labs.scene.control.gauge;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;


/**
 * Created by
 * User: hansolo
 * Date: 13.03.12
 * Time: 15:35
 */
public class SixteenSegmentBuilder {
    private SixteenSegment segment;

    public final SixteenSegmentBuilder create() {
        segment = new SixteenSegment();
        return this;
    }

    public final SixteenSegmentBuilder character(final String CHARACTER) {
        segment.setCharacter(CHARACTER);
        return this;
    }

    public final SixteenSegmentBuilder character(final Character CHARACTER) {
        segment.setCharacter(CHARACTER);
        return this;
    }

    public final SixteenSegmentBuilder dotOn(final boolean DOT_ON) {
        segment.setDotOn(DOT_ON);
        return this;
    }

    public final SixteenSegmentBuilder customSegmentMapping(final Map<Integer, List<SixteenSegment.Segment>> CUSTOM_SEGMENT_MAPPING) {
        segment.setCustomSegmentMapping(CUSTOM_SEGMENT_MAPPING);
        return this;
    }

    public final SixteenSegmentBuilder color(final Color COLOR) {
        segment.setColor(COLOR);
        return this;
    }

    public final SixteenSegmentBuilder plainColor(final boolean PLAIN_COLOR) {
        segment.setPlainColor(PLAIN_COLOR);
        return this;
    }

    public final SixteenSegment build() {
        return segment != null ? segment : new SixteenSegment();
    }
}
