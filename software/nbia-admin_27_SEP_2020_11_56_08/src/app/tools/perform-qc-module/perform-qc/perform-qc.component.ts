import { Component, OnDestroy, OnInit } from '@angular/core';
import { Consts } from '@app/constants';
import { ApiService } from '@app/admin-common/services/api.service';
import { UtilService } from '@app/admin-common/services/util.service';
import { takeUntil } from 'rxjs/operators';
import { QuerySectionService } from '../../query-section-module/services/query-section.service';
import { Subject } from 'rxjs';
import { LoadingDisplayService } from '@app/admin-common/components/loading-display/loading-display.service';


@Component( {
    selector: 'nbia-perform-qc',
    templateUrl: './perform-qc.component.html',
    styleUrls: ['./perform-qc.component.scss']
} )

/**
 * The parent component for "Perform QC"
 */
export class PerformQcComponent implements OnInit, OnDestroy{
    consts = Consts;
    userRoles;
    roleIsGood = false;
    currentQueryData = [];
    showBulkOperations = false;
    collectionSite = '';

    searchResults = {};
    searchResultsSelectedCount = 0;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private apiService: ApiService, private utilService: UtilService,
                 private querySectionService: QuerySectionService, private loadingDisplayService: LoadingDisplayService ) {
    }

    async ngOnInit() {

        this.apiService.updatedUserRolesEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.userRoles = data;
                if( this.userRoles !== undefined && this.userRoles.indexOf( 'NCIA.MANAGE_VISIBILITY_STATUS' ) > -1 ){
                    this.roleIsGood = true;
                }
            });
        this.apiService.getRoles();


        this.querySectionService.updatedQueryEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.currentQueryData = data;
            this.collectionSite = this.getCollectionSite();
                this.loadingDisplayService.setLoading( true, 'Searching...' );
                this.doPerformQcSearch();
            } );

        // Rerun the current query
        this.apiService.submitBulkQcResultsEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            (data) => {
                // Rerun the query
                this.doPerformQcSearch();
            });



        /**
         * Check for search results.  If there are none, don't show bulk operations.
         */
        this.apiService.searchResultsEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            data => {
                this.showBulkOperations = data[0] !== Consts.NO_SEARCH && data.length > 0;
            } );

    }

    // Run the query
    doPerformQcSearch() {
        this.apiService.doCriteriaSearchQuery( Consts.TOOL_PERFORM_QC, this.currentQueryData );
    }

    onSearchResultsUpdate( e ) {
        if( !this.utilService.isNullOrUndefinedOrEmpty( e ) ){
            this.searchResults = e;
        }
    }

    onResultsSelectCountUpdate( e ) {
        this.searchResultsSelectedCount = e;
    }

    getCollectionSite(){
        for( let row of this.currentQueryData){
            if( row['criteria'] === Consts.QUERY_CRITERIA_COLLECTION){
                return row['data'];
            }
        }
        return '';
    }

    ngOnDestroy() {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
