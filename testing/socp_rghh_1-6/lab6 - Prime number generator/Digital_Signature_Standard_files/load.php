var isCompatible=function(){if(navigator.appVersion.indexOf('MSIE')!==-1&&parseFloat(navigator.appVersion.split('MSIE')[1])<6){return false;}return true;};var startUp=function(){mediaWiki.loader.register([["site","1303746685",[],"site"],["startup",1304947731,[],"startup"],["user","1145459700",[],"user"],["user.options","1145459700",[],"private"],["skins.vector",1304454599,[]],["skins.simple",1297545884,[]],["jquery",1297546087,[]],["jquery.async",1297546087,[]],["jquery.autoEllipsis",1297546087,["jquery.highlightText"]],["jquery.checkboxShiftClick",1297546087,[]],["jquery.client",1297417818,[]],["jquery.collapsibleTabs",1304454600,[]],["jquery.color",1297546087,[]],["jquery.cookie",1297546087,[]],["jquery.delayedBind",1297546087,[]],["jquery.expandableField",1297546087,[]],["jquery.highlightText",1297546087,[]],["jquery.placeholder",1300655278,[]],["jquery.json",1304003229,[]],["jquery.localize",1297546087,[]],["jquery.suggestions",1297546087,[]],["jquery.tabIndex",1297546087,[]],[
"jquery.textSelection",1300632620,[]],["jquery.tipsy",1300130330,[]],["jquery.ui.core",1297546096,["jquery"]],["jquery.ui.widget",1297546096,[]],["jquery.ui.mouse",1297546096,["jquery.ui.widget"]],["jquery.ui.position",1297546096,[]],["jquery.ui.draggable",1297546096,["jquery.ui.core","jquery.ui.mouse","jquery.ui.widget"]],["jquery.ui.droppable",1297546096,["jquery.ui.core","jquery.ui.mouse","jquery.ui.widget","jquery.ui.draggable"]],["jquery.ui.resizable",1297546096,["jquery.ui.core","jquery.ui.widget","jquery.ui.mouse"]],["jquery.ui.selectable",1297546096,["jquery.ui.core","jquery.ui.widget","jquery.ui.mouse"]],["jquery.ui.sortable",1297546096,["jquery.ui.core","jquery.ui.widget","jquery.ui.mouse"]],["jquery.ui.accordion",1297546096,["jquery.ui.core","jquery.ui.widget"]],["jquery.ui.autocomplete",1297546096,["jquery.ui.core","jquery.ui.widget","jquery.ui.position"]],["jquery.ui.button",1300130330,["jquery.ui.core","jquery.ui.widget"]],["jquery.ui.datepicker",1297546096,[
"jquery.ui.core"]],["jquery.ui.dialog",1297546096,["jquery.ui.core","jquery.ui.widget","jquery.ui.button","jquery.ui.draggable","jquery.ui.mouse","jquery.ui.position","jquery.ui.resizable"]],["jquery.ui.progressbar",1297546096,["jquery.ui.core","jquery.ui.widget"]],["jquery.ui.slider",1297546096,["jquery.ui.core","jquery.ui.widget","jquery.ui.mouse"]],["jquery.ui.tabs",1297546096,["jquery.ui.core","jquery.ui.widget"]],["jquery.effects.core",1297546097,["jquery"]],["jquery.effects.blind",1297546096,["jquery.effects.core"]],["jquery.effects.bounce",1297546097,["jquery.effects.core"]],["jquery.effects.clip",1297546097,["jquery.effects.core"]],["jquery.effects.drop",1297546097,["jquery.effects.core"]],["jquery.effects.explode",1297546097,["jquery.effects.core"]],["jquery.effects.fold",1297546097,["jquery.effects.core"]],["jquery.effects.highlight",1297546097,["jquery.effects.core"]],["jquery.effects.pulsate",1297546097,["jquery.effects.core"]],["jquery.effects.scale",1297546097,[
"jquery.effects.core"]],["jquery.effects.shake",1297546097,["jquery.effects.core"]],["jquery.effects.slide",1297546097,["jquery.effects.core"]],["jquery.effects.transfer",1297546097,["jquery.effects.core"]],["mediawiki",1304947731,[]],["mediawiki.util",1299534314,["jquery.checkboxShiftClick","jquery.client","jquery.placeholder"]],["mediawiki.action.history",1297546088,["mediawiki.legacy.history"],"mediawiki.action.history"],["mediawiki.action.edit",1300126148,[]],["mediawiki.action.view.rightClickEdit",1297546088,[]],["mediawiki.special.preferences","1303871688",[]],["mediawiki.special.search",1297546088,[]],["mediawiki.language",1297546090,[]],["mediawiki.legacy.ajax","1303870105",["mediawiki.legacy.wikibits"]],["mediawiki.legacy.ajaxwatch",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.block",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.commonPrint",1304454599,[]],["mediawiki.legacy.config",1297545883,["mediawiki.legacy.wikibits"]],[
"mediawiki.legacy.diff",1304454599,["mediawiki.legacy.wikibits"],"mediawiki.action.history"],["mediawiki.legacy.edit",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.enhancedchanges",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.history",1297545883,["mediawiki.legacy.wikibits"],"mediawiki.action.history"],["mediawiki.legacy.htmlform",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.IEFixes",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.metadata","1303870122",["mediawiki.legacy.wikibits"]],["mediawiki.legacy.mwsuggest","1303870105",["mediawiki.legacy.wikibits"]],["mediawiki.legacy.prefs",1297545883,["mediawiki.legacy.wikibits","mediawiki.legacy.htmlform"]],["mediawiki.legacy.preview",1297841696,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.protect",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.search",1300126148,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.shared",1304454599,[]],["mediawiki.legacy.oldshared",
1297545883,[]],["mediawiki.legacy.upload",1297545883,["mediawiki.legacy.wikibits"]],["mediawiki.legacy.wikibits","1303870105",["mediawiki.language"]],["mediawiki.legacy.wikiprintable",1297545883,[]],["ext.gadget.HideFlaggedRevs","1297722991",[]],["ext.gadget.roundCorners","1276629524",[]],["ext.gadget.dropdown-menus","1297188992",[]],["ext.checkUser",1297545985,["mediawiki.legacy.block"]],["ext.collection.jquery.json",1297545942,[]],["ext.collection.jquery.jstorage",1297545942,["ext.collection.jquery.json"]],["ext.collection.suggest",1297545942,["ext.collection.bookcreator"]],["ext.collection",1297545942,["ext.collection.bookcreator","jquery.ui.sortable"]],["ext.collection.bookcreator",1297545942,["ext.collection.jquery.jstorage"]],["ext.collection.checkLoadFromLocalStorage",1297545942,["ext.collection.jquery.jstorage"]],["ext.vector.collapsibleNav","1303870105",["jquery.client","jquery.cookie","jquery.tabIndex"],"ext.vector"],["ext.vector.collapsibleTabs",1304454600,[
"jquery.collapsibleTabs","jquery.delayedBind"],"ext.vector"],["ext.vector.editWarning","1303870105",[],"ext.vector"],["ext.vector.expandableSearch",1297545946,["jquery.client","jquery.expandableField","jquery.delayedBind"],"ext.vector"],["ext.vector.footerCleanup",1297545946,[],"ext.vector"],["ext.vector.sectionEditLinks",1300402032,["jquery.cookie","jquery.clickTracking"],"ext.vector"],["ext.vector.simpleSearch","1303870105",["jquery.client","jquery.suggestions","jquery.autoEllipsis","jquery.placeholder"],"ext.vector"],["contentCollector",1297545930,[],"ext.wikiEditor"],["jquery.wikiEditor","1303870146",["jquery.client","jquery.textSelection","jquery.delayedBind"],"ext.wikiEditor"],["jquery.wikiEditor.iframe",1297868152,["jquery.wikiEditor","contentCollector"],"ext.wikiEditor"],["jquery.wikiEditor.dialogs",1301840189,["jquery.wikiEditor","jquery.wikiEditor.toolbar","jquery.ui.dialog","jquery.ui.button","jquery.ui.draggable","jquery.ui.resizable","jquery.tabIndex"],"ext.wikiEditor"],[
"jquery.wikiEditor.highlight",1297545931,["jquery.wikiEditor","jquery.wikiEditor.iframe"],"ext.wikiEditor"],["jquery.wikiEditor.preview",1297545931,["jquery.wikiEditor"],"ext.wikiEditor"],["jquery.wikiEditor.previewDialog",1297545931,["jquery.wikiEditor","jquery.wikiEditor.dialogs"],"ext.wikiEditor"],["jquery.wikiEditor.publish",1297545931,["jquery.wikiEditor","jquery.wikiEditor.dialogs"],"ext.wikiEditor"],["jquery.wikiEditor.templateEditor",1297545930,["jquery.wikiEditor","jquery.wikiEditor.iframe","jquery.wikiEditor.dialogs"],"ext.wikiEditor"],["jquery.wikiEditor.templates",1297545931,["jquery.wikiEditor","jquery.wikiEditor.iframe"],"ext.wikiEditor"],["jquery.wikiEditor.toc",1297545931,["jquery.wikiEditor","jquery.wikiEditor.iframe","jquery.ui.draggable","jquery.ui.resizable","jquery.autoEllipsis","jquery.color"],"ext.wikiEditor"],["jquery.wikiEditor.toolbar",1298232871,["jquery.wikiEditor"],"ext.wikiEditor"],["ext.wikiEditor",1297545931,["jquery.wikiEditor"],"ext.wikiEditor"],[
"ext.wikiEditor.dialogs",1297545931,["ext.wikiEditor","ext.wikiEditor.toolbar","jquery.wikiEditor.dialogs","jquery.suggestions"],"ext.wikiEditor"],["ext.wikiEditor.highlight",1297545930,["ext.wikiEditor","jquery.wikiEditor.highlight"],"ext.wikiEditor"],["ext.wikiEditor.preview",1297545931,["ext.wikiEditor","jquery.wikiEditor.preview"],"ext.wikiEditor"],["ext.wikiEditor.previewDialog","1303918010",["ext.wikiEditor","jquery.wikiEditor.previewDialog"],"ext.wikiEditor"],["ext.wikiEditor.publish",1297545931,["ext.wikiEditor","jquery.wikiEditor.publish"],"ext.wikiEditor"],["ext.wikiEditor.templateEditor",1297545931,["ext.wikiEditor","ext.wikiEditor.highlight","jquery.wikiEditor.templateEditor"],"ext.wikiEditor"],["ext.wikiEditor.templates",1297545931,["ext.wikiEditor","ext.wikiEditor.highlight","jquery.wikiEditor.templates"],"ext.wikiEditor"],["ext.wikiEditor.toc",1297545931,["ext.wikiEditor","ext.wikiEditor.highlight","jquery.wikiEditor.toc"],"ext.wikiEditor"],[
"ext.wikiEditor.tests.toolbar",1297545930,["ext.wikiEditor.toolbar"],"ext.wikiEditor"],["ext.wikiEditor.toolbar",1298324402,["ext.wikiEditor","ext.wikiEditor.toolbar.i18n","jquery.wikiEditor.toolbar","jquery.cookie","jquery.async"],"ext.wikiEditor"],["ext.wikiEditor.toolbar.i18n","1145459700",[],"ext.wikiEditor"],["ext.prefSwitch",1297546041,["jquery.client"]]]);mediaWiki.config.set({"wgLoadScript":"http://bits.wikimedia.org/ru.wikipedia.org/load.php","debug":false,"skin":"vector","stylepath":"http://bits.wikimedia.org/skins-1.17","wgUrlProtocols":"http\\:\\/\\/|https\\:\\/\\/|ftp\\:\\/\\/|irc\\:\\/\\/|gopher\\:\\/\\/|telnet\\:\\/\\/|nntp\\:\\/\\/|worldwind\\:\\/\\/|mailto\\:|news\\:|svn\\:\\/\\/|git\\:\\/\\/|mms\\:\\/\\/","wgArticlePath":"/wiki/$1","wgScriptPath":"/w","wgScriptExtension":".php","wgScript":"/w/index.php","wgVariantArticlePath":false,"wgActionPaths":[],"wgServer":"http://ru.wikipedia.org","wgUserLanguage":"ru","wgContentLanguage":"ru","wgVersion":"1.17wmf1",
"wgEnableAPI":true,"wgEnableWriteAPI":true,"wgSeparatorTransformTable":[",	."," 	,"],"wgDigitTransformTable":["",""],"wgMainPageTitle":"Заглавная страница","wgFormattedNamespaces":{"-2":"Медиа","-1":"Служебная","0":"","1":"Обсуждение","2":"Участник","3":"Обсуждение участника","4":"Википедия","5":"Обсуждение Википедии","6":"Файл","7":"Обсуждение файла","8":"MediaWiki","9":"Обсуждение MediaWiki","10":"Шаблон","11":"Обсуждение шаблона","12":"Справка","13":"Обсуждение справки","14":"Категория","15":"Обсуждение категории","100":"Портал","101":"Обсуждение портала"},"wgNamespaceIds":{"медиа":-2,"служебная":-1,"":0,"обсуждение":1,"участник":2,"обсуждение_участника":3,"википедия":4,
"обсуждение_википедии":5,"файл":6,"обсуждение_файла":7,"mediawiki":8,"обсуждение_mediawiki":9,"шаблон":10,"обсуждение_шаблона":11,"справка":12,"обсуждение_справки":13,"категория":14,"обсуждение_категории":15,"портал":100,"обсуждение_портала":101,"участница":2,"обсуждение участницы":3,"изображение":6,"обсуждение_изображения":7,"обсуждение_участницы":3,"вп":4,"image":6,"image_talk":7},"wgSiteName":"Википедия","wgFileExtensions":["png","gif","jpg","jpeg","xcf","pdf","mid","ogg","ogv","svg","djvu","tiff","tif","oga"],"wgDBname":"ruwiki","wgExtensionAssetsPath":"http://bits.wikimedia.org/w/extensions-1.17","wgMWSuggestTemplate":"http://ru.wikipedia.org/w/api.php?action=opensearch\x26search={searchTerms}\x26namespace={namespaces}\x26suggest",
"wgCollectionVersion":"1.4","wgCollapsibleNavBucketTest":false,"wgCollapsibleNavForceNewVersion":false});};if(isCompatible()){document.write("\x3cscript src=\"http://bits.wikimedia.org/ru.wikipedia.org/load.php?debug=false\x26amp;lang=ru\x26amp;modules=jquery%7Cmediawiki\x26amp;only=scripts\x26amp;skin=vector\x26amp;version=20110509T132820Z\" type=\"text/javascript\"\x3e\x3c/script\x3e");}delete isCompatible;;

/* cache key: ruwiki:resourceloader:filter:minify-js:5:59d6b213f1e05d091dd2892e1f955dca */