import { Component, OnDestroy, OnInit } from '@angular/core';
import { ApiService } from '@app/admin-common/services/api.service';
import { UtilService } from '@app/admin-common/services/util.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { Consts } from '@app/constants';
import { QuerySectionService } from '../../query-section-module/services/query-section.service';
import { Properties } from '@assets/properties';


@Component( {
    selector: 'nbia-edit-collection-descriptions',
    templateUrl: './edit-collection-descriptions.component.html',
    styleUrls: ['./edit-collection-descriptions.component.scss', '../../../app.component.scss']
} )

/**
 * Read, edit, save Collection descriptions.
 */
export class EditCollectionDescriptionsComponent implements OnInit, OnDestroy{
    userRoles;
    roleIsGood = false;

    collections;
    currentCollection;
    currentLicenseIndex = 0;
    currentLicenseIndexTrailer = 0;
    currentCollectionIndex = 0;
    showHtml = false;
    consts = Consts;


    // @TODO Most of these configuration values came from a demo.  Look them over, make sure they are good.
    htmlContent = 'The <b>Description</b> text will go here.';
    textTrailer = '';
    licenseIndexTrailer = 0;
    editorConfig: AngularEditorConfig = {
        editable: true,
        spellcheck: true,
        height: 'auto',
        minHeight: '130',
        maxHeight: 'auto',
        width: 'auto',
        minWidth: '0',
        translate: 'yes',
        enableToolbar: true,
        showToolbar: true,
        placeholder: 'Enter text here...',
        defaultParagraphSeparator: '',
        defaultFontName: '',
        defaultFontSize: '',
        fonts: [
            { class: 'arial', name: 'Arial' },
            { class: 'times-new-roman', name: 'Times New Roman' },
            { class: 'calibri', name: 'Calibri' },
            { class: 'comic-sans-ms', name: 'Comic Sans MS' }
        ],
        sanitize: true,
        toolbarPosition: 'top',
        toolbarHiddenButtons: [
            ['heading', 'fontName', 'fontSize', 'color'],
            ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull', 'indent', 'outdent'],
            ['paragraph', 'blockquote', 'removeBlockquote', 'horizontalLine', 'orderedList', 'unorderedList'],
            ['image', 'video', 'insertVideo', 'insertImage', 'customClasses', 'insertHorizontalRule'],
            ['toggleEditorMode', 'unlink']
        ]
    };

    /**
     * The list of licenses we are working with.
     * This one object/license is a place holder for the HTML until the license data makes its way back from the server.
     */
    licData = [{ 'shortName': '', 'longName': '', 'licenseURL': '', 'commercialUse': true, 'id': -1 }];


    /*
    These are tool bar items that can be hidden with toolbarHiddenButtons:
    backgroundColor
    bold
    customClasses
    fontName
    fontSize
    heading
    indent
    insertHorizontalRule
    insertImage
    insertOrderedList
    insertUnorderedList
    insertVideo
    italic
    justifyCenter
    justifyFull
    justifyLeft
    justifyRight
    link
    outdent
    removeFormat
    strikeThrough
    subscript
    superscript
    textColor
    toggleEditorMode
    underline
    unlink
    */

    properties = Properties;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private apiService: ApiService, private utilService: UtilService,
                 private querySectionService: QuerySectionService) {
    }

    ngOnInit() {

        // Receive the Collection names and descriptions.
        this.apiService.collectionsAndDescriptionEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.collections = data;
                if( !this.utilService.isNullOrUndefinedOrEmpty( this.collections ) ){
                    this.currentCollection = this.collections[0]['name'];
                    this.currentLicenseIndex = this.getLicIndexById(this.collections[0]['licenseId']);
                    this.currentLicenseIndexTrailer = this.currentLicenseIndex;
                    this.htmlContent = this.collections[0]['description'];
                    this.textTrailer = this.htmlContent;
                }

            } );

        this.apiService.getCollectionAndDescriptions();


        this.querySectionService.updateCollectionEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.onCollectionClick( data );
            } );


        this.apiService.updatedUserRolesEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.userRoles = data;
                if( this.userRoles !== undefined && this.userRoles.indexOf( 'NCIA.MANAGE_COLLECTION_DESCRIPTION' ) > -1 ){
                    this.roleIsGood = true;
                }
            });
        this.apiService.getRoles();

        // Get the list of licenses and their associated data.
        this.apiService.collectionLicensesResultsEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.licData = data;
                this.licData.sort( ( a, b ) => a['longName'].toUpperCase().localeCompare( b['longName'].toUpperCase() ) );

            } );
        this.apiService.getCollectionLicenses();


    }

    onCollectionClick( i ) {
        this.currentCollection = this.collections[i]['name'];
        this.currentLicenseIndex = this.getLicIndexById(this.collections[i]['licenseId']);
        this.currentLicenseIndexTrailer = this.currentLicenseIndex;
        this.htmlContent = this.collections[i]['description'];
        this.textTrailer = this.htmlContent;
        this.currentCollectionIndex = i;
    }

    onSave() {
        if( Properties.DEMO_MODE){
            console.log('Demo Mode Update Collection description ', this.htmlContent);
        }
        else{
            if( this.textTrailer !== this.htmlContent || this.currentLicenseIndexTrailer !== this.currentLicenseIndex){
                this.apiService.updateCollectionDescription(
                    this.currentCollection.replace( /\/\/.*/, '' ),
                    this.htmlContent,
                    this.licData[this.currentLicenseIndex]['id']
                    );
                this.textTrailer = this.htmlContent;
                this.currentLicenseIndexTrailer = this.currentLicenseIndex;
                this.collections[this.currentCollectionIndex]['description'] = this.htmlContent;
            }
        }
    }

    onLicenseDropdownClick(i){
        this.currentLicenseIndex = i;
    }

    onToggleShowHtml() {
        this.showHtml = (!this.showHtml);
    }

    getLicIndexById( id ) {
        let len = this.licData.length;
        for( let i = 0; i < len; i++ ){
            if( this.licData[i]['id'] === id ){
                return i;
            }
        }
        return 0;
    }

    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
