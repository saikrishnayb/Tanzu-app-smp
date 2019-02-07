/*!
 Responsive 2.0.1
 2014-2015 SpryMedia Ltd - datatables.net/license
*/
! function(e) {
    "function" == typeof define && define.amd ? define(["jquery", "datatables.net"], function(t) {
        return e(t, window, document)
    }) : "object" == typeof exports ? module.exports = function(t, n) {
        return t || (t = window), n && n.fn.dataTable || (n = require("datatables.net")(t, n).$), e(n, t, t.document)
    } : e(jQuery, window, document)
}(function(e, t, n, i) {
    "use strict";
    var r = e.fn.dataTable,
        s = function(t, n) {
            if (!r.versionCheck || !r.versionCheck("1.10.3")) throw "DataTables Responsive requires DataTables 1.10.3 or newer";
            this.s = {
                dt: new r.Api(t),
                columns: [],
                current: []
            }, this.s.dt.settings()[0].responsive || (n && "string" == typeof n.details ? n.details = {
                type: n.details
            } : n && n.details === !1 ? n.details = {
                type: !1
            } : n && n.details === !0 && (n.details = {
                type: "inline"
            }), this.c = e.extend(!0, {}, s.defaults, r.defaults.responsive, n), t.responsive = this, this._constructor())
        };
    e.extend(s.prototype, {
        _constructor: function() {
            var n = this,
                i = this.s.dt,
                s = i.settings()[0],
                o = e(t).width();
            i.settings()[0]._responsive = this, e(t).on("resize.dtr orientationchange.dtr", r.util.throttle(function() {
                var i = e(t).width();
                i !== o && (n._resize(), o = i)
            })), s.oApi._fnCallbackReg(s, "aoRowCreatedCallback", function(t, r, s) {
                -1 !== e.inArray(!1, n.s.current) && e("td, th", t).each(function(t) {
                    var r = i.column.index("toData", t);
                    n.s.current[r] === !1 && e(this).css("display", "none")
                })
            }), i.on("destroy.dtr", function() {
                i.off(".dtr"), e(i.table().body()).off(".dtr"), e(t).off("resize.dtr orientationchange.dtr"), e.each(n.s.current, function(e, t) {
                    t === !1 && n._setColumnVis(e, !0)
                })
            }), this.c.breakpoints.sort(function(e, t) {
                return e.width < t.width ? 1 : e.width > t.width ? -1 : 0
            }), this._classLogic(), this._resizeAuto();
            var a = this.c.details;
            a.type !== !1 && (n._detailsInit(), i.on("column-visibility.dtr", function(e, t, i, r) {
                n._classLogic(), n._resizeAuto(), n._resize()
            }), i.on("draw.dtr", function() {
                n._redrawChildren()
            }), e(i.table().node()).addClass("dtr-" + a.type)), i.on("column-reorder.dtr", function(e, t, i) {
                i.drop && (n._classLogic(), n._resizeAuto(), n._resize())
            }), i.on("column-sizing.dtr", function() {
                n._resize()
            }), i.on("init.dtr", function(t, r, s) {
                n._resizeAuto(), n._resize(), e.inArray(!1, n.s.current) && i.columns.adjust()
            }), this._resize()
        },
        _columnsVisiblity: function(t) {
            var n, i, r = this.s.dt,
                s = this.s.columns,
                o = s.map(function(e, t) {
                    return {
                        columnIdx: t,
                        priority: e.priority
                    }
                }).sort(function(e, t) {
                    return e.priority !== t.priority ? e.priority - t.priority : e.columnIdx - t.columnIdx
                }),
                a = e.map(s, function(n) {
                    return n.auto && null === n.minWidth ? !1 : n.auto === !0 ? "-" : -1 !== e.inArray(t, n.includeIn)
                }),
                d = 0;
            for (n = 0, i = a.length; i > n; n++) a[n] === !0 && (d += s[n].minWidth);
            var l = r.settings()[0].oScroll,
                c = l.sY || l.sX ? l.iBarWidth : 0,
                u = r.table().container().offsetWidth - c,
                h = u - d;
            for (n = 0, i = a.length; i > n; n++) s[n].control && (h -= s[n].minWidth);
            var p = !1;
            for (n = 0, i = o.length; i > n; n++) {
                var f = o[n].columnIdx;
                "-" === a[f] && !s[f].control && s[f].minWidth && (p || h - s[f].minWidth < 0 ? (p = !0, a[f] = !1) : a[f] = !0, h -= s[f].minWidth)
            }
            var m = !1;
            for (n = 0, i = s.length; i > n; n++)
                if (!s[n].control && !s[n].never && !a[n]) {
                    m = !0;
                    break
                }
            for (n = 0, i = s.length; i > n; n++) s[n].control && (a[n] = m);
            return -1 === e.inArray(!0, a) && (a[0] = !0), a
        },
        _classLogic: function() {
            var t = this,
                n = this.c.breakpoints,
                r = this.s.dt,
                s = r.columns().eq(0).map(function(t) {
                    var n = this.column(t),
                        s = n.header().className,
                        o = r.settings()[0].aoColumns[t].responsivePriority;
                    if (o === i) {
                        var a = e(n.header()).data("priority");
                        o = a !== i ? 1 * a : 1e4
                    }
                    return {
                        className: s,
                        includeIn: [],
                        auto: !1,
                        control: !1,
                        never: s.match(/\bnever\b/) ? !0 : !1,
                        priority: o
                    }
                }),
                o = function(t, n) {
                    var i = s[t].includeIn; - 1 === e.inArray(n, i) && i.push(n)
                },
                a = function(e, i, r, a) {
                    var d, l, c;
                    if (r) {
                        if ("max-" === r)
                            for (d = t._find(i).width, l = 0, c = n.length; c > l; l++) n[l].width <= d && o(e, n[l].name);
                        else if ("min-" === r)
                            for (d = t._find(i).width, l = 0, c = n.length; c > l; l++) n[l].width >= d && o(e, n[l].name);
                        else if ("not-" === r)
                            for (l = 0, c = n.length; c > l; l++) - 1 === n[l].name.indexOf(a) && o(e, n[l].name)
                    } else s[e].includeIn.push(i)
                };
            s.each(function(t, i) {
                for (var r = t.className.split(" "), s = !1, o = 0, d = r.length; d > o; o++) {
                    var l = e.trim(r[o]);
                    if ("all" === l) return s = !0, void(t.includeIn = e.map(n, function(e) {
                        return e.name
                    }));
                    if ("none" === l || t.never) return void(s = !0);
                    if ("control" === l) return s = !0, void(t.control = !0);
                    e.each(n, function(e, t) {
                        var n = t.name.split("-"),
                            r = new RegExp("(min\\-|max\\-|not\\-)?(" + n[0] + ")(\\-[_a-zA-Z0-9])?"),
                            o = l.match(r);
                        o && (s = !0, o[2] === n[0] && o[3] === "-" + n[1] ? a(i, t.name, o[1], o[2] + o[3]) : o[2] !== n[0] || o[3] || a(i, t.name, o[1], o[2]))
                    })
                }
                s || (t.auto = !0)
            }), this.s.columns = s
        },
        _detailsDisplay: function(t, n) {
            var i = this,
                r = this.s.dt,
                s = this.c.details;
            if (s && s.type) {
                var o = s.display(t, n, function() {
                    return s.renderer(r, t[0], i._detailsObj(t[0]))
                });
                (o === !0 || o === !1) && e(r.table().node()).triggerHandler("responsive-display.dt", [r, t, o, n])
            }
        },
        _detailsInit: function() {
            var t = this,
                n = this.s.dt,
                i = this.c.details;
            "inline" === i.type && (i.target = "td .dt-responsive-icon, th .dt-responsive-icon"), n.on("draw.dtr", function() {
                t._tabIndexes()
            }), t._tabIndexes(), e(n.table().body()).on("keyup.dtr", "td, th", function(t) {
                13 === t.keyCode && e(this).data("dtr-keyboard") && e(this).click()
            });
            var r = i.target,
                s = "string" == typeof r ? r : "td, th";
            e(n.table().body()).on("click.dtr mousedown.dtr mouseup.dtr", s, function(i) {
                if (e(n.table().node()).hasClass("collapsed") && n.row(e(this).closest("tr")).length) {
                    if ("number" == typeof r) {
                        var s = 0 > r ? n.columns().eq(0).length + r : r;
                        if (n.cell(this).index().column !== s) return
                    }
                    var o = n.row(e(this).closest("tr"));
                    "click" === i.type ? t._detailsDisplay(o, !1) : "mousedown" === i.type ? e(this).css("outline", "none") : "mouseup" === i.type && e(this).blur().css("outline", "")
                }
            })
        },
        _detailsObj: function(t) {
            var n = this,
                i = this.s.dt;
            return e.map(this.s.columns, function(e, r) {
                return e.never ? void 0 : {
                    title: i.settings()[0].aoColumns[r].sTitle,
                    data: i.cell(t, r).render(n.c.orthogonal),
                    hidden: i.column(r).visible() && !n.s.current[r],
                    columnIndex: r,
                    rowIndex: t
                }
            })
        },
        _find: function(e) {
            for (var t = this.c.breakpoints, n = 0, i = t.length; i > n; n++)
                if (t[n].name === e) return t[n]
        },
        _redrawChildren: function() {
            var e = this,
                t = this.s.dt;
            t.rows({
                page: "current"
            }).iterator("row", function(n, i) {
                t.row(i);
                e._detailsDisplay(t.row(i), !0)
            })
        },
        _resize: function() {
            var n, i, r = this,
                s = this.s.dt,
                o = e(t).width(),
                a = this.c.breakpoints,
                d = a[0].name,
                l = this.s.columns,
                c = this.s.current.slice();
            for (n = a.length - 1; n >= 0; n--)
                if (o <= a[n].width) {
                    d = a[n].name;
                    break
                }
            var u = this._columnsVisiblity(d);
            this.s.current = u;
            var h = !1;
            for (n = 0, i = l.length; i > n; n++)
                if (u[n] === !1 && !l[n].never && !l[n].control) {
                    h = !0;
                    break
                }
            e(s.table().node()).toggleClass("collapsed", h);
            var p = !1;
            s.columns().eq(0).each(function(e, t) {
                u[t] !== c[t] && (p = !0, r._setColumnVis(e, u[t]))
            }), p && (this._redrawChildren(), e(s.table().node()).trigger("responsive-resize.dt", [s, this.s.current]))
        },
        _resizeAuto: function() {
            var t = this.s.dt,
                n = this.s.columns;
            if (this.c.auto && -1 !== e.inArray(!0, e.map(n, function(e) {
                    return e.auto
                }))) {
                var r = t.table().node(),
                    s = (r.offsetWidth, t.columns, r.cloneNode(!1)),
                    o = e(t.table().header().cloneNode(!1)).appendTo(s),
                    a = e(t.table().body()).clone(!1, !1).empty().appendTo(s),
                    d = t.columns().header().filter(function(e) {
                        return t.column(e).visible()
                    }).to$().clone(!1).css("display", "table-cell");
                e(a).append(e(t.rows({
                    page: "current"
                }).nodes()).clone(!1)).find("th, td").css("display", "");
                var l = t.table().footer();
                if (l) {
                    var c = e(l.cloneNode(!1)).appendTo(s),
                        u = t.columns().header().filter(function(e) {
                            return t.column(e).visible()
                        }).to$().clone(!1).css("display", "table-cell");
                    e("<tr/>").append(u).appendTo(c)
                }
                if (e("<tr/>").append(d).appendTo(o), "inline" === this.c.details.type) {
                    e(s).addClass("dtr-inline collapsed");
                    var h = e(r),
                        p = h.data("has-icon") === i;
                    p && h.data("has-icon", !0).find("tbody>tr>td:first-child, tbody>tr>th:first-child").each(function() {
                    	
                    	var $this = $(this);
                    	var isHidden = $this.is(':hidden');
                    	
                    	if(isHidden) {
                    		
                    		var keepSearching = true;
                    		
                    		while(keepSearching) {
                    			
                    			var nextTdIsNotHidden = !$this.next().is(':hidden');
                    			
                    			if(nextTdIsNotHidden) {
                    				$this.next().prepend('<i class="dt-responsive-icon"></i>');
                    				keepSearching = false;
                    			}else{
                    				$this = $this.next();
                    			}
                    			
                    		}
                    		
                    	}else {
                    		$this.prepend('<i class="dt-responsive-icon"></i>');
                    	}
                    	
                    });
                }
                var f = e("<div/>").css({
                    width: 1,
                    height: 1,
                    overflow: "hidden"
                }).append(s);
                f.insertBefore(t.table().node()), d.each(function(e) {
                    var i = t.column.index("fromVisible", e);
                    n[i].minWidth = this.offsetWidth || 0
                }), f.remove()
            }
        },
        _setColumnVis: function(t, n) {
            var i = this.s.dt,
                r = n ? "" : "none";
            e(i.column(t).header()).css("display", r), e(i.column(t).footer()).css("display", r), i.column(t).nodes().to$().css("display", r)
        },
        _tabIndexes: function() {
            var t = this.s.dt,
                n = t.cells({
                    page: "current"
                }).nodes().to$(),
                i = t.settings()[0],
                r = this.c.details.target;
            n.filter("[data-dtr-keyboard]").removeData("[data-dtr-keyboard]");
            var s = "number" == typeof r ? ":eq(" + r + ")" : r;
            e(s, t.rows({
                page: "current"
            }).nodes()).attr("tabIndex", -1).data("dtr-keyboard", 1)
        }
    }), s.breakpoints = [{
        name: "desktop",
        width: 1 / 0
    }, {
        name: "tablet-l",
        width: 1024
    }, {
        name: "tablet-p",
        width: 768
    }, {
        name: "mobile-l",
        width: 480
    }, {
        name: "mobile-p",
        width: 320
    }], s.display = {
        childRow: function(t, n, i) {
            return n ? e(t.node()).hasClass("parent") ? (t.child(i(), "child").show(), !0) : void 0 : t.child.isShown() ? (t.child(!1), e(t.node()).removeClass("parent"), !1) : (t.child(i(), "child").show(), e(t.node()).addClass("parent"), !0)
        },
        childRowImmediate: function(t, n, i) {
            return !n && t.child.isShown() || !t.responsive.hasHidden() ? (t.child(!1), e(t.node()).removeClass("parent"), !1) : (t.child(i(), "child").show(), e(t.node()).addClass("parent"), !0)
        },
        modal: function(t) {
            return function(i, r, s) {
                if (r) e("div.dtr-modal-content").empty().append(s());
                else {
                    var o = function() {
                            a.remove(), e(n).off("keypress.dtr")
                        },
                        a = e('<div class="dtr-modal"/>').append(e('<div class="dtr-modal-display"/>').append(e('<div class="dtr-modal-content"/>').append(s())).append(e('<div class="dtr-modal-close">&times;</div>').click(function() {
                            o()
                        }))).append(e('<div class="dtr-modal-background"/>').click(function() {
                            o()
                        })).appendTo("body");
                    e(n).on("keyup.dtr", function(e) {
                        27 === e.keyCode && (e.stopPropagation(), o())
                    })
                }
                t && t.header && e("div.dtr-modal-content").prepend("<h2>" + t.header(i) + "</h2>")
            }
        }
    }, s.defaults = {
        breakpoints: s.breakpoints,
        auto: !0,
        details: {
            display: s.display.childRow,
            renderer: function(t, n, i) {
                var r = e.map(i, function(e, t) {
                    return e.hidden ? '<li data-dtr-index="' + e.columnIndex + '" data-dt-row="' + e.rowIndex + '" data-dt-column="' + e.columnIndex + '"><span class="dtr-title">' + e.title + '</span> <span class="dtr-data">' + e.data + "</span></li>" : ""
                }).join("");
                return r ? e('<ul data-dtr-index="' + n + '"/>').append(r) : !1
            },
            target: 0,
            type: "inline"
        },
        orthogonal: "display"
    };
    var o = e.fn.dataTable.Api;
    return o.register("responsive()", function() {
        return this
    }), o.register("responsive.index()", function(t) {
        return t = e(t), {
            column: t.data("dtr-index"),
            row: t.parent().data("dtr-index")
        }
    }), o.register("responsive.rebuild()", function() {
        return this.iterator("table", function(e) {
            e._responsive && e._responsive._classLogic()
        })
    }), o.register("responsive.recalc()", function() {
        return this.iterator("table", function(e) {
            e._responsive && (e._responsive._resizeAuto(), e._responsive._resize())
        })
    }), o.register("responsive.hasHidden()", function() {
        var t = this.context[0];
        return t._responsive ? -1 !== e.inArray(!1, t._responsive.s.current) : !1
    }), s.version = "2.0.2-dev", e.fn.dataTable.Responsive = s, e.fn.DataTable.Responsive = s, e(n).on("preInit.dt.dtr", function(t, n, i) {
        if ("dt" === t.namespace && (e(n.nTable).hasClass("responsive") || e(n.nTable).hasClass("dt-responsive") || n.oInit.responsive || r.defaults.responsive)) {
            var o = n.oInit.responsive;
            o !== !1 && new s(n, e.isPlainObject(o) ? o : {})
        }
    }), s
});