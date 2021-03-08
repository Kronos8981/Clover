/*
 * Clover - 4chan browser https://github.com/Floens/Clover/
 * Copyright (C) 2014  Floens
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.floens.chan.core.site.sites.indiachan;

import androidx.annotation.Nullable;

import org.floens.chan.core.model.Post;
import org.floens.chan.core.model.orm.Board;
import org.floens.chan.core.model.orm.Loadable;
import org.floens.chan.core.site.Site;
import org.floens.chan.core.site.SiteIcon;
import org.floens.chan.core.site.common.CommonSite;
import org.floens.chan.core.site.common.vichan.VichanActions;
import org.floens.chan.core.site.common.vichan.VichanApi;
import org.floens.chan.core.site.common.vichan.VichanCommentParser;
import org.floens.chan.core.site.common.vichan.VichanEndpoints;

import okhttp3.HttpUrl;

public class Indiachan extends CommonSite {
    public static final CommonSiteUrlHandler URL_HANDLER = new CommonSiteUrlHandler() {
        @Override
        public Class<? extends Site> getSiteClass() {
            return Indiachan.class;
        }

        @Override
        public HttpUrl getUrl() {
            return HttpUrl.parse("https://indiachan.com/");
        }

        @Override
        public String[] getNames() {
            return new String[]{"indiachan"};
        }

        @Override
        public String desktopUrl(Loadable loadable, @Nullable Post post) {
            if (loadable.isCatalogMode()) {
                return getUrl().newBuilder().addPathSegment(loadable.boardCode).toString();
            } else if (loadable.isThreadMode()) {
                return getUrl().newBuilder()
                        .addPathSegment(loadable.boardCode).addPathSegment("res")
                        .addPathSegment(String.valueOf(loadable.no) + ".html")
                        .toString();
            } else {
                return getUrl().toString();
            }
        }
    };

    @Override
    public void setup() {
        setName("Indiachan");
        setIcon(SiteIcon.fromFavicon(HttpUrl.parse("https://indiachan.com/.static/favicon.png")));

        setBoards(
                Board.fromSiteNameCode(this, "Anime & Manga", "a"),
                Board.fromSiteNameCode(this, "Random", "b"),
                Board.fromSiteNameCode(this, "Technology", "g"),
                Board.fromSiteNameCode(this, "Politically Incorrect", "pol"),
                Board.fromSiteNameCode(this, "Films and Sports", "ent"),
                Board.fromSiteNameCode(this, "Health and Fitness", "yoga"),
                Board.fromSiteNameCode(this, "Business", "dhan"),
                Board.fromSiteNameCode(this, "Indiachan Discussion", "meta"),
        );

        setResolvable(URL_HANDLER);

        setConfig(new CommonConfig() {
            @Override
            public boolean feature(Feature feature) {
                return feature == Feature.POSTING;
            }
        });

        setEndpoints(new VichanEndpoints(this,
                "https://indiachan.com",
                "https://indiachan.com"));
        setActions(new VichanActions(this));
        setApi(new VichanApi(this));
        setParser(new VichanCommentParser());
    }
}
